package ru.nsu.g.solovev5.m.task212.models.messages;

public record ProcessResultMessage(
    boolean hasNonPrime
) implements TcpMessage {
    @Override
    public MessageType getType() {
        return MessageType.RESULT;
    }
}
