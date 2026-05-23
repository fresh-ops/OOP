package ru.nsu.g.solovev5.m.task212.models.responses;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ResponseInputStream implements AutoCloseable {
    private final ObjectInputStream stream;

    public ResponseInputStream(InputStream stream) throws IOException {
        this.stream = new ObjectInputStream(stream);
    }

    public Response readResponse() throws IOException, ClassNotFoundException {
        var object = stream.readObject();
        if (object instanceof Response) {
            return (Response) object;
        }
        throw new IOException("Invalid object received");
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }
}
