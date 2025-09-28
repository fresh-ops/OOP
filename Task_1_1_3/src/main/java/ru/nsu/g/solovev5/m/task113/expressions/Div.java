package ru.nsu.g.solovev5.m.task113.expressions;

import java.util.Map;

/**
 * A division operation.
 */
public class Div extends BinaryOperation {
    /**
     * Constructs a new division operation.
     *
     * @param left  a left operand
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
    protected int eval(Map<String, Integer> assignment) {
        return left.eval(assignment) / right.eval(assignment);
    }

    @Override
    public String toString() {
        return String.format("(%s / %s)", left, right);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Div div)) {
            return false;
        }

        return left.equals(div.left) && right.equals(div.right);
    }

    @Override
    public int hashCode() {
        return left.hashCode() * 100 + right.hashCode() * 10 + "Div".hashCode();
    }
}
