package ru.nsu.g.solovev5.m.task113.expressions;

/**
 * An exception occurred when passed an assignment in wrong format.
 */
public class WrongAssignmentFormatException extends EvaluationException {
    /**
     * Constructs a new wrong assignment format exception.
     *
     * @param entry the entry with wrong format
     */
    public WrongAssignmentFormatException(String entry) {
        super("Assignment error in \"" + entry + "\"");
    }
}
