package ru.nsu.g.solovev5.m.task212.master.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class SlaveConnector implements Runnable {
    private final ServerSocket server;
    private final Consumer<Socket> consumer;

    public SlaveConnector(ServerSocket server, Consumer<Socket> consumer) {
        this.server = server;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                var slave = server.accept();
                consumer.accept(slave);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
