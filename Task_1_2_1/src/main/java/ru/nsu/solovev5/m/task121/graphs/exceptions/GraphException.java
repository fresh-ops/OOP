package ru.nsu.solovev5.m.task121.graphs.exceptions;

/**
 * A basic graph operation exception.
 */
public abstract class GraphException extends RuntimeException {
    /**
     * Constructs a new graph exception.
     *
     * @param message the detail message
     */
    public GraphException(String message) {
        super(message);
    }
}
