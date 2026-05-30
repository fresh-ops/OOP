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
    private static final int CHUNK_POLL_DELAY = 1_000;
    private static final int PING_TIMEOUT = 2_000;

    private final MessageChannel channel;
    private final Queue<Chunk> chunkQueue = new ConcurrentLinkedQueue<>();
    private final UUID uuid;

    private volatile boolean running = false;
    private volatile boolean busy = false;

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
                if (chunkQueue.isEmpty()) {
                    if (!ping()) {
                        System.out.println("No response");
                        break;
                    }
                } else {
                    busy = true;
                    var chunk = chunkQueue.poll();
                    try {
                        handleChunk(chunk);
                    } catch (ClassNotFoundException e) {
                        System.err.println("Corrupted response");
                        break;
                    }
                    busy = false;
                }
                try {
                    Thread.sleep(CHUNK_POLL_DELAY);
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

    public boolean isBusy() {
        return busy;
    }

    /**
     * Adds a new chunk to queue.
     *
     * @param chunk a chunk to process
     */
    public void addChunk(Chunk chunk) {
        chunkQueue.add(chunk);
    }

    /**
     * Closes this event loop.
     *
     * @throws IOException if an I/O error occurred
     */
    public void close() throws IOException {
        running = false;
        busy = false;
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
            var response = channel.receive(PING_TIMEOUT);
            return response instanceof PingMessage;
        } catch (ClassNotFoundException | SocketTimeoutException e) {
            return false;
        }
    }

    private void handleChunk(Chunk chunk) throws IOException, ClassNotFoundException {
        var request = new ProcessMessage(
            chunk.numbers()
        );
        channel.send(request);
        var response = channel.receive();
        if (response instanceof ProcessResultMessage resultMessage) {
            chunk.onResult().accept(chunk, resultMessage.hasNonPrime());
        }
    }
}
