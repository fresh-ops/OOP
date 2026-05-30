package ru.nsu.g.solovev5.m.task212.actors.master.events;

import java.io.IOException;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import ru.nsu.g.solovev5.m.task212.actors.master.network.SlaveSession;
import ru.nsu.g.solovev5.m.task212.models.messages.ConnectionApprovedMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.ConnectionRefusedMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.ConnectionRequestMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.TcpMessage;

/**
 * A connection event loop.
 */
public class SlaveEventLoop implements Runnable {
    private final SlaveSession session;
    private final Queue<Event> events = new ConcurrentLinkedQueue<>();
    private final UUID uuid;

    private volatile boolean running = false;

    /**
     * Creates a new SlaveEventLoop.
     *
     * @param session a session for message exchanging.
     */
    public SlaveEventLoop(SlaveSession session, UUID uuid) {
        this.session = session;
        this.uuid = uuid;
    }

    @Override
    public void run() {
        try {
            if (authorizeConnection()) {
                running = true;
            }

            while (running && !Thread.interrupted() && !session.isClosed()) {
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
        session.close();
    }

    private boolean authorizeConnection() throws IOException {
        try {
            TcpMessage message = session.receive();
            if (!(message instanceof ConnectionRequestMessage request)) {
                session.send(new ConnectionRefusedMessage("Unexpected connection message"));
                return false;
            }

            if (uuid.equals(request.expectedUuid())) {
                session.send(new ConnectionApprovedMessage());
                return true;
            }

            session.send(new ConnectionRefusedMessage("Master UUID mismatch"));
            return false;
        } catch (ClassNotFoundException e) {
            session.send(new ConnectionRefusedMessage("Invalid connection request"));
            return false;
        }
    }

    private void ping() throws IOException {
        System.out.println("ping");
    }
}
