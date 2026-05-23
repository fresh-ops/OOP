package ru.nsu.g.solovev5.m.task212.models.requests;

import java.io.Serializable;

public abstract class Request implements Serializable {
    public static final RequestType TYPE = RequestType.UNKNOWN;

    public RequestType getType() {
        return TYPE;
    }
}
