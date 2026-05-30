package ru.nsu.g.solovev5.m.task212.actors.master.events;

import java.io.IOException;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import ru.nsu.g.solovev5.m.task212.models.messages.ConnectionApprovedMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.ConnectionRefusedMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.ConnectionRequestMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.TcpMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.io.MessageChannel;

/**
 * A connection event loop.
 */
public class SlaveEventLoop implements Runnable {
    private final MessageChannel channel;
    private final Queue<Event> events = new ConcurrentLinkedQueue<>();
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
                if (events.isEmpty()) {
                    ping();
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
     * Adds a new event to loop.
     *
     * @param event a new event
     */
    public void addEvent(Event event) {
        events.add(event);
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

    private void ping() throws IOException {
        System.out.println("ping");
    }
}
