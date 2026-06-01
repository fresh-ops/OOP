package ru.nsu.g.solovev5.m.task212.models.messages;

/**
 * A message responding the process request.
 *
 * @param hasNonPrime result of processing
 */
public record ProcessResultMessage(
    boolean hasNonPrime
) implements TcpMessage {
    @Override
    public MessageType getType() {
        return MessageType.RESULT;
    }
}
