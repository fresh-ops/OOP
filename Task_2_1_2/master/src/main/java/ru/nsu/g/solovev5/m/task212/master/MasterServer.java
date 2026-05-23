package ru.nsu.g.solovev5.m.task212.master;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import ru.nsu.g.solovev5.m.task212.master.eventloop.SlaveEventLoop;
import ru.nsu.g.solovev5.m.task212.master.network.SlaveConnector;
import ru.nsu.g.solovev5.m.task212.master.network.SlaveSession;

public class MasterServer implements Runnable {
    private final ServerSocket server;
    private final SlaveConnector connector;
    private final List<SlaveEventLoop> slaves;
    private final List<Thread> slaveThreads;

    public MasterServer() throws IOException {
        server = new ServerSocket(2120);
        connector = new SlaveConnector(server, this::handleNewSession);
        slaves = new ArrayList<>();
        slaveThreads = new ArrayList<>();
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
        try {
            session.performHandshake();
        } catch (IOException e) {
            System.err.println("Handshake failed: " + e.getMessage());
            try {
                session.close();
            } catch (IOException e1) {
                System.err.println("Failed to close session: " + e.getMessage());
            }
        }

        var loop = new SlaveEventLoop(session);
        slaves.add(loop);
        var thread =  new Thread(loop);
        thread.start();
        slaveThreads.add(thread);
    }

    private void cleanResources() {
        System.out.println("Cleaning resources");

        for (var slave :  slaves) {
            try {
                slave.close();
            } catch (IOException e) {
                System.err.println("Could not close session: " + e.getMessage());
            }
        }

        for (var thread : slaveThreads) {
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Could not join thread: " + e.getMessage());
            }
        }

        try {
            this.server.close();
        } catch (IOException e) {
            System.err.println("Could not close server: " + e.getMessage());
        }
    }
}
