package ru.nsu.g.solovev5.m.task212.actors.master.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.function.Consumer;

/**
 * A worker for accepting new connections.
 */
public class SlaveConnector implements Runnable {
    private final ServerSocket server;
    private final Consumer<SlaveSession> consumer;

    /**
     * Creates a new SlaveConnector.
     *
     * @param server   an opened server
     * @param consumer a new connections consumer
     */
    public SlaveConnector(ServerSocket server, Consumer<SlaveSession> consumer) {
        this.server = server;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                var socket = server.accept();
                var slave = new SlaveSession(socket);
                consumer.accept(slave);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
