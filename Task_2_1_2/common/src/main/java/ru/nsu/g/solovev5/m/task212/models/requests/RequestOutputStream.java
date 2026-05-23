package ru.nsu.g.solovev5.m.task212.models.requests;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class RequestOutputStream implements AutoCloseable {
    private final ObjectOutputStream stream;

    public RequestOutputStream(OutputStream stream) throws IOException {
        this.stream = new ObjectOutputStream(stream);
        this.stream.flush();
    }

    public void writeRequest(Request request) throws IOException {
        stream.writeObject(request);
    }

    public void flush() throws IOException {
        stream.flush();
    }

    public void close() throws IOException {
        stream.close();
    }
}
