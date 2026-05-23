package ru.nsu.g.solovev5.m.task212.models.responses;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ResponseOutputStream implements AutoCloseable {
    private final ObjectOutputStream stream;

    public ResponseOutputStream(OutputStream stream) throws IOException {
        this.stream = new ObjectOutputStream(stream);
        this.stream.flush();
    }

    public void writeResponse(Response response) throws IOException {
        stream.writeObject(response);
    }

    public void flush() throws IOException {
        stream.flush();
    }

    public void close() throws IOException {
        stream.close();
    }
}
