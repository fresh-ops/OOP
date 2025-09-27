package ru.nsu.g.solovev5.m.task113.expressions;

import java.util.HashMap;

/**
 * A division operation.
 */
public class Div extends BinaryOperation {
    /**
     * Constructs a new division operation.
     *
     * @param left a left operand
     * @param right a right operand
     */
    public Div(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Expression derivative(String variable) {
        var firstPart = new Mul(right, left.derivative(variable));
        var secondPart = new Mul(left, right.derivative(variable));
        var thirdPart = new Mul(right, right);

        return new Div(new Sub(firstPart, secondPart), thirdPart);
    }

    @Override
    protected int eval(HashMap<String, Integer> assignment) {
        return left.eval(assignment) / right.eval(assignment);
    }

    @Override
    public String toString() {
        return String.format("(%s / %s)", left, right);
    }
}
