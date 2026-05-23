package ru.nsu.g.solovev5.m.task212.master;

import java.io.IOException;
import java.net.ServerSocket;
import ru.nsu.g.solovev5.m.task212.master.network.SlaveConnector;
import ru.nsu.g.solovev5.m.task212.master.network.SlaveSession;

public class MasterServer implements Runnable {
    private final ServerSocket server;
    private final SlaveConnector connector;

    public MasterServer() throws IOException {
        server = new ServerSocket(2120);
        connector = new SlaveConnector(server, this::handleNewSession);
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

    private void handleNewSession(SlaveSession session) {
        try (session) {
            session.out.write("ping\n".getBytes());
            session.out.flush();
            System.out.println("Send ping request");
            var response = session.in.readLine();
            System.out.println("New session message received: " + response);
        } catch (IOException e) {
            System.err.println("Failed to send ping request: " + e.getMessage());
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
