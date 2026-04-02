package ru.nsu.g.solovev5.m.task231.application.exceptions;

/**
 * An exception occurred in the application layer.
 */
public abstract class ApplicationException extends RuntimeException {
    /**
     * Creates a new ApplicationException with the given message.
     *
     * @param message the details message
     */
    public ApplicationException(String message) {
        super(message);
    }
}
