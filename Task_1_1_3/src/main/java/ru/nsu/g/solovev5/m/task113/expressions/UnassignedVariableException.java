package ru.nsu.g.solovev5.m.task113.expressions;

public class UnassignedVariableException extends EvaluationException {
    public UnassignedVariableException(String name) {
        super("No assignment for variable " + name);
    }
}
