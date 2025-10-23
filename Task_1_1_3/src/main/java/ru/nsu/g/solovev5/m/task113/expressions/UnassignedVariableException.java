package ru.nsu.g.solovev5.m.task113.expressions;

/**
 * An exception occurred when a variable in an expression does not have any assignment.
 */
public class UnassignedVariableException extends EvaluationException {
    /**
     * Constructs a new unassigned variable exception.
     *
     * @param name a name of the unassigned variable
     */
    public UnassignedVariableException(String name) {
        super("No assignment for variable " + name);
    }
}
