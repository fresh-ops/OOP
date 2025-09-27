package ru.nsu.g.solovev5.m.task113.expressions;

import java.util.HashMap;

/**
 * An addition operation.
 */
public class Add extends BinaryOperation {
    /**
     * Constructs a new addition operation.
     *
     * @param left a left operand
     * @param right a right operand
     */
    public Add(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    @Override
    protected int eval(HashMap<String, Integer> assignment) {
        return left.eval(assignment) + right.eval(assignment);
    }

    @Override
    public String toString() {
        return String.format("(%s + %s)", left, right);
    }
}
