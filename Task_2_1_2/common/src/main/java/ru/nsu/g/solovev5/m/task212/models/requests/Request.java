package ru.nsu.g.solovev5.m.task212.models.requests;

import java.io.Serializable;

public abstract class Request implements Serializable {
    public abstract RequestType getType();
}
