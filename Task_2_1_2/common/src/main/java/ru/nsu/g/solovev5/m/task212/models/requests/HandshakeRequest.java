package ru.nsu.g.solovev5.m.task212.models.requests;

public class HandshakeRequest extends Request {
    public static final RequestType TYPE = RequestType.HANDSHAKE;

    @Override
    public RequestType getType() {
        return TYPE;
    }
}
