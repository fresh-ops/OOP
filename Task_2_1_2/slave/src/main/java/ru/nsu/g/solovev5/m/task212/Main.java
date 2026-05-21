package ru.nsu.g.solovev5.m.task212;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        try (var socket = new Socket("localhost", 2120)) {
            System.out.println("Connected to " + socket.getInetAddress());
        } catch (UnknownHostException e) {
            System.err.println("Unknown host");
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        } finally {
            System.out.println("Connection closed");
        }
    }
}