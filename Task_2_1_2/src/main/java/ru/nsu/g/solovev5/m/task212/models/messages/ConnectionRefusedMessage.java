package ru.nsu.g.solovev5.m.task212.models.messages;

/**
 * A message refusing connection.
 *
 * @param reason detailed reason
 */
public record ConnectionRefusedMessage(
    String reason
) implements TcpMessage {
    @Override
    public MessageType getType() {
        return MessageType.CONNECTION_REFUSED;
    }
}
