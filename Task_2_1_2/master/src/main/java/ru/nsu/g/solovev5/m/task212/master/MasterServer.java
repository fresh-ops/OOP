package ru.nsu.g.solovev5.m.task212.master;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import ru.nsu.g.solovev5.m.task212.master.network.SlaveConnector;

public class MasterServer implements Runnable {
    private final ServerSocket server;
    private final SlaveConnector connector;

    public MasterServer() throws IOException {
        server = new ServerSocket(2120);
        connector = new SlaveConnector(server, this::handleNewConnections);
    }

    @Override
    public void run() {
        try {
            System.out.println("MasterServer is listening at port " + server.getLocalPort());
            connector.run();
        } finally {
            cleanResources();
        }
    }

    private void handleNewConnections(Socket socket) {
        System.out.println("Connected: " + socket.getInetAddress().getHostName());
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Could not close socket: " + e.getMessage());
        }
    }

    private void cleanResources() {
        System.out.println("Cleaning resources");
        try {
            this.server.close();
        } catch (IOException e) {
            System.err.println("Could not close server: " + e.getMessage());
        }
    }
}
