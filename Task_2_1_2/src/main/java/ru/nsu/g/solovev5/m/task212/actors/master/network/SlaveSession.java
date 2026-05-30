package ru.nsu.g.solovev5.m.task212.actors.master.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import ru.nsu.g.solovev5.m.task212.models.messages.TcpMessage;
import ru.nsu.g.solovev5.m.task212.models.messages.io.TcpMessageInputStream;
import ru.nsu.g.solovev5.m.task212.models.messages.io.TcpMessageOutputStream;

/**
 * A connection object for message exchanging.
 */
public class SlaveSession implements AutoCloseable {
    private final Socket socket;
    private final TcpMessageInputStream input;
    private final TcpMessageOutputStream output;

    /**
     * Creates a new SlaveSession.
     *
     * @param socket a socket connection
     */
    public SlaveSession(Socket socket) {
        this.socket = socket;
        try {
            OutputStream socketOutput = socket.getOutputStream();
            InputStream socketInput = socket.getInputStream();
            this.output = new TcpMessageOutputStream(socketOutput);
            this.input = new TcpMessageInputStream(socketInput);
            this.output.flush();
        } catch (IOException e) {
            throw new IllegalStateException("Could not initialize message streams", e);
        }
    }

    /**
     * Sends a TCP message to the connected slave.
     *
     * @param message a message to send
     * @throws IOException if an I/O error occurred
     */
    public void send(TcpMessage message) throws IOException {
        output.writeMessage(message);
    }

    /**
     * Reads the next TCP message from the connected slave.
     *
     * @return a received message
     * @throws IOException            if an I/O error occurred
     * @throws ClassNotFoundException if a class of a serialized object cannot be found
     */
    public TcpMessage receive() throws IOException, ClassNotFoundException {
        return input.readMessage();
    }

    /**
     * Closes this connection.
     *
     * @throws IOException if an I/O error occurred
     */
    public void close() throws IOException {
        input.close();
        output.close();
        socket.close();
    }

    /**
     * Tests if this session is closed.
     *
     * @return {@code true} if this session is closed, {@code false} otherwise
     */
    public boolean isClosed() {
        return socket.isClosed();
    }
}
