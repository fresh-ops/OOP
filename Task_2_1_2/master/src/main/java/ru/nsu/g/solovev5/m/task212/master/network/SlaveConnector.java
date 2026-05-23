package ru.nsu.g.solovev5.m.task212.master.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.function.Consumer;

public class SlaveConnector implements Runnable {
    private final ServerSocket server;
    private final Consumer<SlaveSession> consumer;

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
