package ru.nsu.g.solovev5.m.task212.models.messages;

/**
 * Enumerates types of TCP messages.
 */
public enum MessageType {
    CONNECTION_REQUEST,
    CONNECTION_APPROVED,
    CONNECTION_REFUSED,
    PING,
    PROCESS,
    RESULT,
}
