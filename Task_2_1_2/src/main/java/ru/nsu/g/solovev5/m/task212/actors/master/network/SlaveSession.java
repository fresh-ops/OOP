package ru.nsu.g.solovev5.m.task212.actors.master.network;

import java.io.IOException;
import java.net.Socket;

/**
 * A connection object for message exchanging.
 */
public class SlaveSession implements AutoCloseable {
    private final Socket socket;

    /**
     * Creates a new SlaveSession.
     *
     * @param socket a socket connection
     */
    public SlaveSession(Socket socket) {
        this.socket = socket;
    }

    /**
     * Closes this connection.
     *
     * @throws IOException if an I/O error occurred
     */
    public void close() throws IOException {
        socket.close();
    }
}
