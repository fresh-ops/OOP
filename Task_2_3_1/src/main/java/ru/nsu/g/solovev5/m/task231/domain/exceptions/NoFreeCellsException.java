package ru.nsu.g.solovev5.m.task231.domain.exceptions;

/**
 * An exception occurred when there is no free cells to pick.
 */
public class NoFreeCellsException extends DomainException {
    /**
     * Creates a new NoFreeCellsException.
     */
    public NoFreeCellsException() {
        super("No free cells available to pick");
    }
}
