package ru.nsu.g.solovev5.m.task113.parser;

/**
 * An exception occurred when an operator is unsupported.
 */
public class UnsupportedOperatorException extends ParserException {
    /**
     * Constructs a new unsupported operator exception.
     *
     * @param operator an unsupported operator
     */
    public UnsupportedOperatorException(String operator) {
        super(String.format("Unsupported operator \"%s\"", operator));
    }
}
