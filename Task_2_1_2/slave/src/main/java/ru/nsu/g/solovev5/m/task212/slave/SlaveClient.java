package ru.nsu.g.solovev5.m.task212.slave;

import java.io.IOException;
import java.net.Socket;
import ru.nsu.g.solovev5.m.task212.models.requests.RequestInputStream;
import ru.nsu.g.solovev5.m.task212.models.responses.ResponseOutputStream;

public class SlaveClient implements AutoCloseable {
    private final Socket socket;
    public final RequestInputStream requests;
    public final ResponseOutputStream responses;

    public SlaveClient(Socket socket) throws IOException {
        this.socket = socket;
        requests = new RequestInputStream(this.socket.getInputStream());
        responses = new ResponseOutputStream(this.socket.getOutputStream());
        responses.flush();
    }

    public void close() throws IOException {
        socket.close();
    }
}
