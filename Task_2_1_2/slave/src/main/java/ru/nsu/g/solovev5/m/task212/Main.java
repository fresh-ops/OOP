package ru.nsu.g.solovev5.m.task212;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import ru.nsu.g.solovev5.m.task212.slave.SlaveClient;

public class Main {
    public static void main(String[] args) {
        try (var client = new SlaveClient(new Socket("localhost", 2120))) {
            var request = client.in.readLine();
            System.out.println("Received request: " + request);
            if (request.equals("ping")) {
                client.out.println("pong");
            } else {
                client.out.println("unknown");
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host");
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        } finally {
            System.out.println("Connection closed");
        }
    }
}