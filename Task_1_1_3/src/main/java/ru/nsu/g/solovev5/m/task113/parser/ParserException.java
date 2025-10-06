package ru.nsu.g.solovev5.m.task113.parser;

/**
 * A basic parser exception.
 */
abstract class ParserException extends RuntimeException {
    /**
     * Constructs a new parser exception.
     *
     * @param message the detail message
     */
    public ParserException(String message) {
        super(message);
    }
}
