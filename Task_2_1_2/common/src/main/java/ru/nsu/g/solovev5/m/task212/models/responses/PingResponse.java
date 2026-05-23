package ru.nsu.g.solovev5.m.task212.models.responses;

import ru.nsu.g.solovev5.m.task212.models.requests.RequestType;

public class PingResponse extends Response {
    public static final ResponseType TYPE = ResponseType.PING;

    @Override
    public ResponseType getType() {
        return TYPE;
    }
}
