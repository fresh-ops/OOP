package ru.nsu.g.solovev5.m.task231.application.exceptions;

/**
 * An exception occurred when there is no free cells to pick.
 */
public class NoFreeCellsException extends ApplicationException {
    /**
     * Creates a new NoFreeCellsException.
     */
    public NoFreeCellsException() {
        super("No free cells available to pick");
    }
}
