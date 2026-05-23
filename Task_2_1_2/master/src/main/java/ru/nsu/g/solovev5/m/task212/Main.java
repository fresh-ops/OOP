package ru.nsu.g.solovev5.m.task212;

import java.io.IOException;
import ru.nsu.g.solovev5.m.task212.master.MasterServer;

public class Main {
    public static void main(String[] args) {
        try {
            var server = new MasterServer();
            System.out.println("Starting server...");
            server.run();
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
        }
    }
}