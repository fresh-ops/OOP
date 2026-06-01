package ru.nsu.g.solovev5.m.task212.models.messages;

/**
 * A message requesting the data processing.
 *
 * @param numbers numbers to process
 */
public record ProcessMessage(
    int[] numbers
) implements TcpMessage {
    @Override
    public MessageType getType() {
        return MessageType.PROCESS;
    }
}
