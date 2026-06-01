package ru.nsu.g.solovev5.m.task212.models.messages.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import ru.nsu.g.solovev5.m.task212.models.messages.TcpMessage;

/**
 * A typed object input stream for TCP messages.
 */
public final class TcpMessageInputStream implements AutoCloseable {
    private final ObjectInputStream input;

    /**
     * Creates a new message input stream.
     *
     * @param inputStream a source input stream
     * @throws IOException if an I/O error occurred
     */
    public TcpMessageInputStream(InputStream inputStream) throws IOException {
        this.input = new ObjectInputStream(inputStream);
    }

    /**
     * Reads the next TCP message from the stream.
     *
     * @return a received message
     * @throws IOException            if an I/O error occurred or the object is not a TCP message
     * @throws ClassNotFoundException if a class of a serialized object cannot be found
     */
    public TcpMessage readMessage() throws IOException, ClassNotFoundException {
        var message = input.readObject();
        if (message instanceof TcpMessage tcpMessage) {
            return tcpMessage;
        }

        if (message == null) {
            throw new IOException("Received null message");
        }

        throw new IOException("Unexpected message type: " + message.getClass().getName());
    }

    @Override
    public void close() throws IOException {
        input.close();
    }
}