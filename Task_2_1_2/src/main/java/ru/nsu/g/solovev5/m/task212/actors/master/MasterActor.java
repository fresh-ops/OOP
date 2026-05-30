package ru.nsu.g.solovev5.m.task212.actors.master;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import ru.nsu.g.solovev5.m.task212.actors.Actor;
import ru.nsu.g.solovev5.m.task212.actors.Role;
import ru.nsu.g.solovev5.m.task212.actors.master.events.SlaveEventLoop;
import ru.nsu.g.solovev5.m.task212.actors.master.network.SlaveConnector;
import ru.nsu.g.solovev5.m.task212.actors.master.network.SlaveSession;
import ru.nsu.g.solovev5.m.task212.models.messages.AdvertisementMessage;

/**
 * An actor that accepts tasks and distributes them between slaves.
 */
public class MasterActor implements Actor {
    private final ServerSocket serverSocket = new ServerSocket(0);
    private final UUID uuid = UUID.randomUUID();
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private final List<SlaveEventLoop> loops = new ArrayList<>();

    private volatile boolean running = false;

    /**
     * Creates a new MasterActor.
     *
     * @throws IOException if an I/O error occurred
     */
    public MasterActor() throws IOException {
    }

    /**
     * Creates an advertisement message with information about server.
     *
     * @return a new advertisement message
     */
    public AdvertisementMessage getAdvertisementMessage() {
        return new AdvertisementMessage(
            uuid,
            serverSocket.getLocalPort()
        );
    }

    @Override
    public Role getRole() {
        return Role.MASTER;
    }

    @Override
    public void run() {
        running = true;
        var connector = new SlaveConnector(serverSocket, this::onConnectionRequest);
        threadPool.submit(connector);
        threadPool.submit(this::readTasks);
    }

    @Override
    public void stop() {
        running = false;
        threadPool.shutdownNow();
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Could not close the server socket");
        }
    }

    /**
     * Reads tasks from standard input.
     */
    private void readTasks() {
        var scanner = new Scanner(System.in);
        while (running && !Thread.interrupted()) {
            var task = scanner.nextLine();
            System.out.println("New task: " + task);
        }
    }

    private void onConnectionRequest(SlaveSession session) {
        var loop = new SlaveEventLoop(session);
        loops.add(loop);
        threadPool.submit(loop);
    }
}
