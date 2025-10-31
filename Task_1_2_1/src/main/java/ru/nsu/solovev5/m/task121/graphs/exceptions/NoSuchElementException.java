package ru.nsu.solovev5.m.task121.graphs.exceptions;

/**
 * An exception occurred when this does not have the specified element.
 */
public abstract class NoSuchElementException extends GraphException {
    /**
     * Constructs a new no such element exception.
     *
     * @param message a detail message
     */
    public NoSuchElementException(String message) {
        super(message);
    }
}
