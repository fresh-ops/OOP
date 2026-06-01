package ru.nsu.g.solovev5.m.task212.models.messages.io;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import ru.nsu.g.solovev5.m.task212.models.messages.TcpMessage;

/**
 * A typed object output stream for TCP messages.
 */
public final class TcpMessageOutputStream implements AutoCloseable {
    private final ObjectOutputStream output;

    /**
     * Creates a new message output stream.
     *
     * @param outputStream a target output stream
     * @throws IOException if an I/O error occurred
     */
    public TcpMessageOutputStream(OutputStream outputStream) throws IOException {
        this.output = new ObjectOutputStream(outputStream);
        this.output.flush();
    }

    /**
     * Writes a TCP message to the stream.
     *
     * @param message a message to write
     * @throws IOException if an I/O error occurred
     */
    public void writeMessage(TcpMessage message) throws IOException {
        output.writeObject(message);
        output.flush();
    }

    /**
     * Flushes the underlying stream.
     *
     * @throws IOException if an I/O error occurred
     */
    public void flush() throws IOException {
        output.flush();
    }

    @Override
    public void close() throws IOException {
        output.close();
    }
}