package ru.nsu.g.solovev5.m.task212.models.messages;

import java.io.Serializable;

/**
 * A contract of TCP messages.
 */
public interface TcpMessage extends Serializable {
    /**
     * Returns the type of this message.
     *
     * @return this message type
     */
    MessageType getType();
}
