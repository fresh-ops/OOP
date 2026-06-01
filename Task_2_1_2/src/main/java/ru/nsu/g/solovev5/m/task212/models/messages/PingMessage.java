package ru.nsu.g.solovev5.m.task212.models.messages;

/**
 * A keepalive ping message.
 */
public record PingMessage() implements TcpMessage {
    @Override
    public MessageType getType() {
        return MessageType.PING;
    }
}