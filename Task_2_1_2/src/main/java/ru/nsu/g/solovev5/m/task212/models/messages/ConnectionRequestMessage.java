package ru.nsu.g.solovev5.m.task212.models.messages;

import java.util.UUID;

/**
 * A connection request.
 *
 * @param expectedUuid the uuid client expects to connect to
 */
public record ConnectionRequestMessage(
    UUID expectedUuid
) implements TcpMessage {
    @Override
    public MessageType getType() {
        return MessageType.CONNECTION_REQUEST;
    }
}
