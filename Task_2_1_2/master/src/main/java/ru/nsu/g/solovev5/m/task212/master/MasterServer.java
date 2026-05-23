package ru.nsu.g.solovev5.m.task212.master;

import java.io.IOException;
import java.net.ServerSocket;
import ru.nsu.g.solovev5.m.task212.master.network.SlaveConnector;
import ru.nsu.g.solovev5.m.task212.master.network.SlaveSession;
import ru.nsu.g.solovev5.m.task212.models.requests.PingRequest;
import ru.nsu.g.solovev5.m.task212.models.requests.RequestOutputStream;
import ru.nsu.g.solovev5.m.task212.models.responses.ResponseInputStream;

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
            System.out.println("New connection");
            var out = new RequestOutputStream(session.out);
            out.writeRequest(new PingRequest());
            out.flush();
            var in = new ResponseInputStream(session.in);
            var response = in.readResponse();
            System.out.println("Received response: " + response.toString());
        } catch (IOException e) {
            System.err.println("Ping request failed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found");
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
