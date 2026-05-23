package ru.nsu.g.solovev5.m.task212.models.responses;

import java.io.Serializable;

public abstract class Response implements Serializable {
    public static final ResponseType TYPE = ResponseType.UNKNOWN;

    public ResponseType getType() {
        return TYPE;
    }
}
