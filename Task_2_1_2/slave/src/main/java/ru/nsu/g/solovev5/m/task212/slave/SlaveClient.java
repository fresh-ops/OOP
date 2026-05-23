package ru.nsu.g.solovev5.m.task212.slave;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SlaveClient implements AutoCloseable {
    private final Socket socket;
    public final InputStream in;
    public final OutputStream out;

    public SlaveClient(Socket socket) throws IOException {
        this.socket = socket;
        in = socket.getInputStream();
        out = this.socket.getOutputStream();
    }

    public void close() throws IOException {
        socket.close();
    }
}
