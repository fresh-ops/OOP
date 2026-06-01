package ru.nsu.g.solovev5.m.task231.domain.exceptions;

/**
 * An exception occurred when trying to pop the tail of the snake with only one segment.
 */
public class SnakeZeroLengthException extends DomainException {
    /**
     * Create a new SnakeZeroLengthException.
     */
    public SnakeZeroLengthException() {
        super("Cannot pop the tail from the snake with only one segment");
    }
}
