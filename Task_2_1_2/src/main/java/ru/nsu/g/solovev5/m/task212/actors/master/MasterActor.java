package ru.nsu.g.solovev5.m.task212.actors.master;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import ru.nsu.g.solovev5.m.task212.actors.Actor;
import ru.nsu.g.solovev5.m.task212.actors.Role;
import ru.nsu.g.solovev5.m.task212.actors.master.events.Chunk;
import ru.nsu.g.solovev5.m.task212.actors.master.events.SlaveEventLoop;
import ru.nsu.g.solovev5.m.task212.actors.master.events.Task;
import ru.nsu.g.solovev5.m.task212.actors.master.network.SlaveConnector;
import ru.nsu.g.solovev5.m.task212.models.messages.AdvertisementMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.io.MessageChannel;

/**
 * An actor that accepts tasks and distributes them between slaves.
 */
public class MasterActor implements Actor {
    private final ServerSocket serverSocket = new ServerSocket(0);
    private final UUID uuid = UUID.randomUUID();
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private final List<SlaveEventLoop> loops = new ArrayList<>();
    private volatile boolean alive = false;

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
        alive = true;
        var connector = new SlaveConnector(serverSocket, this::onConnectionRequest);
        threadPool.submit(connector);
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void stop() {
        alive = false;
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
    public void readTasks() {
        var scanner = new Scanner(System.in);
        System.out.println("Enter numbers separated by spaces(or empty to quit)");
        while (alive && !Thread.interrupted()) {
            var line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            try {
                var numbers = Arrays.stream(line.replaceAll("\\s+", " ").split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
                loops.removeIf(loop -> !loop.isRunning());
                var task = new Task(numbers, loops.size());
                executeTask(task);
                System.out.println("Has non-prime numbers: " + task.getResult());
            } catch (NumberFormatException e) {
                System.err.println("Wrong number format: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void executeTask(Task task) {
        while (!task.isDone() && !Thread.interrupted()) {
            for (var loop : loops) {
                if (loop.isRunning() && !loop.isBusy()) {
                    loop.addChunk(task.nextChunk());
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void onConnectionRequest(MessageChannel channel) {
        var loop = new SlaveEventLoop(channel, uuid);
        loops.add(loop);
        threadPool.submit(loop);
    }
}
