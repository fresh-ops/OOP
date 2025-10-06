package ru.nsu.g.solovev5.m.task113.expressions;

/**
 * A basic class for exception occurred while evaluating an expression.
 */
public abstract class EvaluationException extends RuntimeException {
    /**
     * Constructs a new evaluation exception.
     *
     * @param message the detail message
     */
    public EvaluationException(String message) {
        super(message);
    }
}
