package ru.nsu.g.solovev5.m.task212.models.messages;

/**
 * A message approving connection request.
 */
public record ConnectionApprovedMessage() implements TcpMessage {
    @Override
    public MessageType getType() {
        return MessageType.CONNECTION_APPROVED;
    }
}
