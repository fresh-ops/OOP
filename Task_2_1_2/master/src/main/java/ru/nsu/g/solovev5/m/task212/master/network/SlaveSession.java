package ru.nsu.g.solovev5.m.task212.master.network;

import java.io.IOException;
import java.net.Socket;
import ru.nsu.g.solovev5.m.task212.models.requests.HandshakeRequest;
import ru.nsu.g.solovev5.m.task212.models.requests.RequestOutputStream;
import ru.nsu.g.solovev5.m.task212.models.responses.ResponseInputStream;
import ru.nsu.g.solovev5.m.task212.models.responses.ResponseType;

public class SlaveSession implements AutoCloseable {
    private final Socket socket;
    public final RequestOutputStream requests;
    public final ResponseInputStream responses;

    public SlaveSession(Socket socket) throws IOException {
        this.socket = socket;
        requests = new RequestOutputStream(this.socket.getOutputStream());
        requests.flush();
        responses = new ResponseInputStream(this.socket.getInputStream());
    }

    public void performHandshake() throws IOException {
        requests.writeRequest(new HandshakeRequest());
        requests.flush();
        try {
            var response = responses.readResponse();
            if (response.getType() != ResponseType.HANDSHAKE) {
                throw new IOException("Wrong response type");
            }
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
    }

    public void close() throws IOException {
        socket.close();
    }
}
