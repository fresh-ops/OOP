package ru.nsu.g.solovev5.m.task212;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import ru.nsu.g.solovev5.m.task212.models.responses.PingResponse;
import ru.nsu.g.solovev5.m.task212.slave.SlaveClient;

public class Main {
    public static void main(String[] args) {
        try (var client = new SlaveClient(new Socket("localhost", 2120))) {
            System.out.println("Connected");
            var request = client.requests.readRequest();
            System.out.println("Request: " + request.toString());
            client.responses.writeResponse(new PingResponse());
            client.responses.flush();
        } catch (UnknownHostException e) {
            System.err.println("Unknown host");
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found");
        } finally {
            System.out.println("Connection closed");
        }
    }
}