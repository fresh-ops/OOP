package ru.nsu.g.solovev5.m.task212.actors.master.events;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import ru.nsu.g.solovev5.m.task212.models.messages.ConnectionApprovedMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.ConnectionRefusedMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.ConnectionRequestMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.PingMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.ProcessMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.ProcessResultMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.TcpMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.io.MessageChannel;

/**
 * A connection event loop.
 */
public class SlaveEventLoop implements Runnable {
    private final MessageChannel channel;
    private final Queue<ProcessFrame> processQueue = new ConcurrentLinkedQueue<>();
    private final UUID uuid;

    private volatile boolean running = false;

    /**
     * Creates a new SlaveEventLoop.
     *
     * @param channel a message channel
     */
    public SlaveEventLoop(MessageChannel channel, UUID uuid) {
        this.channel = channel;
        this.uuid = uuid;
    }

    @Override
    public void run() {
        try {
            if (authorizeConnection()) {
                running = true;
            }
            while (running && !Thread.interrupted() && !channel.isClosed()) {
                if (processQueue.isEmpty()) {
                    System.out.println("Ping");
                    if (!ping()) {
                        System.out.println("No response");
                        break;
                    }
                } else {
                    var frame = processQueue.poll();
                    var request = new ProcessMessage(
                        frame.numbers()
                    );
                    channel.send(request);
                    try {
                        var response = channel.receive();
                        if (response instanceof ProcessResultMessage resultMessage) {
                            if (resultMessage.hasNonPrime()) {
                                frame.onSuccess().accept(frame.id());
                            } else {
                                frame.onFail().accept(frame.id());
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        System.out.println("Unable to parse response");
                        break;
                    }
                }
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
        }
        try {
            close();
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
        }
    }

    /**
     * Tests if this event loop is running.
     *
     * @return {@code true} if loop is running, {@code false} otherwise
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Adds a new frame to queue.
     *
     * @param frame a frame to process
     */
    public void addFrame(ProcessFrame frame) {
        processQueue.add(frame);
    }

    /**
     * Closes this event loop.
     *
     * @throws IOException if an I/O error occurred
     */
    public void close() throws IOException {
        running = false;
        channel.close();
    }

    private boolean authorizeConnection() throws IOException {
        try {
            TcpMessage message = channel.receive();
            if (!(message instanceof ConnectionRequestMessage request)) {
                channel.send(new ConnectionRefusedMessage("Unexpected connection message"));
                return false;
            }

            if (uuid.equals(request.expectedUuid())) {
                channel.send(new ConnectionApprovedMessage());
                return true;
            }

            channel.send(new ConnectionRefusedMessage("Master UUID mismatch"));
            return false;
        } catch (ClassNotFoundException e) {
            channel.send(new ConnectionRefusedMessage("Invalid connection request"));
            return false;
        }
    }

    private boolean ping() throws IOException {
        channel.send(new PingMessage());
        try {
            var response = channel.receive(2_000);
            return response instanceof PingMessage;
        } catch (ClassNotFoundException | SocketTimeoutException e) {
            return false;
        }
    }
}
