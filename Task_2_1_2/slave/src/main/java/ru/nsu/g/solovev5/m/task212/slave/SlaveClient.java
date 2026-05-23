package ru.nsu.g.solovev5.m.task212.slave;

import java.io.IOException;
import java.net.Socket;
import ru.nsu.g.solovev5.m.task212.models.requests.RequestInputStream;
import ru.nsu.g.solovev5.m.task212.models.requests.RequestType;
import ru.nsu.g.solovev5.m.task212.models.responses.HandshakeResponse;
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

    public void performHandshake() throws IOException {
        try {
            var request = requests.readRequest();
            if (request.getType() != RequestType.HANDSHAKE) {
                throw new IOException("Invalid request type: " +  request.getType());
            }
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }

        responses.writeResponse(new HandshakeResponse());
        responses.flush();
    }

    public void close() throws IOException {
        socket.close();
    }
}
