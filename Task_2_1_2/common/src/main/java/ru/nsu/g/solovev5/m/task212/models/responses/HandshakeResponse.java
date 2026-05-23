package ru.nsu.g.solovev5.m.task212.models.responses;

public class HandshakeResponse extends Response {
    public static final ResponseType TYPE = ResponseType.HANDSHAKE;

    @Override
    public ResponseType getType() {
        return TYPE;
    }
}
