package ru.nsu.g.solovev5.m.task212.models.requests;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class RequestInputStream implements AutoCloseable {
    private final ObjectInputStream stream;

    public RequestInputStream(InputStream stream) throws IOException {
        this.stream = new ObjectInputStream(stream);
    }

    public Request readRequest() throws IOException, ClassNotFoundException {
        var object = stream.readObject();
        if (object instanceof Request) {
            return (Request) object;
        }
        throw new IOException("Invalid object received");
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }
}
