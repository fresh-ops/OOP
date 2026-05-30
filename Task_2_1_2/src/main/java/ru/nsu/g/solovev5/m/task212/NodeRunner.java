package ru.nsu.g.solovev5.m.task212;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import ru.nsu.g.solovev5.m.task212.actors.Actor;
import ru.nsu.g.solovev5.m.task212.actors.master.MasterActor;
import ru.nsu.g.solovev5.m.task212.models.DiscoveredNode;
import ru.nsu.g.solovev5.m.task212.services.AdvertisementService;
import ru.nsu.g.solovev5.m.task212.services.DiscoveryService;
import ru.nsu.g.solovev5.m.task212.services.ElectionService;

/**
 * A main class that controls services communication and lifecycle.
 */
public class NodeRunner implements Runnable {
    public static final String DISCOVERY_IP = "230.0.0.1";
    public static final int DISCOVERY_PORT = 4446;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final AdvertisementService advertisement;
    private final DiscoveryService discovery;
    private final ElectionService election;

    private ScheduledFuture<?> electionHandle = null;
    private Actor actor;

    /**
     * Starts up the node.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        var runner = new NodeRunner();
        runner.run();
    }

    /**
     * Creates a new NodeRunner.
     */
    public NodeRunner() {
        advertisement = new AdvertisementService(
            DISCOVERY_IP,
            DISCOVERY_PORT
        );

        discovery = new DiscoveryService(
            DISCOVERY_IP,
            DISCOVERY_PORT
        );

        election = new ElectionService();
    }

    @Override
    public void run() {
        try {
            setActor(new MasterActor());
            startAdvertisement();
            startElection();
        } catch (IOException e) {
            System.err.println("Failed to set actor");
            cleanUpResources();
            return;
        }

        try {
            Thread.sleep(15_000);
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted");
        }

        cleanUpResources();
    }

    private void setActor(Actor actor) {
        this.actor = actor;
    }

    /**
     * Starts advertisement process.
     */
    private void startAdvertisement() {
        if (!(actor instanceof MasterActor)) {
            throw new IllegalArgumentException("Cannot create advertisement for non-master");
        }

        var message = ((MasterActor) actor).getAdvertisementMessage();
        advertisement.setMessage(message.toBytes());
        try {
            advertisement.open();
        } catch (IOException e) {
            System.err.println("Advertisement start failed");
        }
        scheduler.scheduleAtFixedRate(advertisement, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * Starts election process.
     */
    private void startElection() {
        if (!(actor instanceof MasterActor)) {
            throw new IllegalArgumentException("Cannot election for non-master");
        }

        var message = ((MasterActor) actor).getAdvertisementMessage();
        try {
            discovery.open();
        } catch (IOException e) {
            System.err.println("Discovery start failed");
        }
        election.reset(message);

        electionHandle = scheduler.scheduleAtFixedRate(() -> {
            var discovered = discovery.discover();
            if (election.elect(discovered)) {
                onMasterElected(discovered);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * Runs when a new master is elected.
     *
     * @param node the new master
     */
    private void onMasterElected(DiscoveredNode node) {
        try {
            discovery.close();
            if (electionHandle != null) {
                electionHandle.cancel(false);
                electionHandle = null;
            }
        } catch (IOException e) {
            System.err.println("Discovery start failed");
        }
        System.err.println("MasterElected: " + node.toString());
    }

    /**
     * Finishes threads and cleans up resources.
     */
    private void cleanUpResources() {
        scheduler.shutdown();

        if (advertisement.isAlive()) {
            try {
                advertisement.close();
            } catch (IOException e) {
                System.err.println("Advertisement shutdown failed");
            }
        }
        if (discovery.isAlive()) {
            try {
                discovery.close();
            } catch (IOException e) {
                System.err.println("Discovery shutdown failed");
            }
        }
    }
}
