package ru.nsu.g.solovev5.m.task212.slave;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SlaveClient implements AutoCloseable {
    private final Socket socket;
    public final BufferedReader in;
    public final PrintStream out;

    public SlaveClient(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new PrintStream(this.socket.getOutputStream(), true, StandardCharsets.UTF_8);
    }

    public void close() throws IOException {
        socket.close();
    }
}
