package ru.nsu.g.solovev5.m.task212;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import ru.nsu.g.solovev5.m.task212.services.AdvertisementService;
import ru.nsu.g.solovev5.m.task212.services.DiscoveryService;

/**
 * A main class that controls services communication and lifecycle.
 */
public class NodeRunner implements Runnable {
    public static final String DISCOVERY_IP = "230.0.0.1";
    public static final int DISCOVERY_PORT = 4446;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final AdvertisementService advertisement;
    private final DiscoveryService discovery;

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
        var message =  UUID.randomUUID().toString();
        advertisement.setMessage(message.getBytes(StandardCharsets.UTF_8));
        try {
            advertisement.open();
        } catch (IOException e) {
            System.err.println("Advertisement start failed");
        }

        discovery = new DiscoveryService(
            DISCOVERY_IP,
            DISCOVERY_PORT
        );
        try {
            discovery.open();
        } catch (IOException e) {
            System.err.println("Discovery start failed");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        scheduleServices();
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted");
        }

        cleanUpResources();
    }

    /**
     * Schedules services.
     */
    private void scheduleServices() {
        scheduler.scheduleAtFixedRate(advertisement, 0, 1, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(() -> {
            var bytes = discovery.receive();
            if (bytes != null) {
                var message =  new String(bytes, StandardCharsets.UTF_8);
                System.out.println(message);
            }
        }, 0, 1, TimeUnit.SECONDS);
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
