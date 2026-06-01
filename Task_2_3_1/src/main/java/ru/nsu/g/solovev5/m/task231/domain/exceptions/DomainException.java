package ru.nsu.g.solovev5.m.task231.domain.exceptions;

/**
 * An exception occurred int the domain layer.
 */
public abstract class DomainException extends RuntimeException {
    /**
     * Creates a new DomainException with the given message.
     *
     * @param message the details message
     */
    public DomainException(String message) {
        super(message);
    }
}
