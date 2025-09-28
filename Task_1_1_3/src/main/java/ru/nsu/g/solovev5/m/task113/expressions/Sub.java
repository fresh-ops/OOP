package ru.nsu.g.solovev5.m.task113.expressions;

import java.util.Map;

/**
 * A substraction operation.
 */
public class Sub extends BinaryOperation {
    /**
     * Constructs a new substraction operation.
     *
     * @param left a left operand
     * @param right a right operand
     */
    public Sub(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    @Override
    protected int eval(Map<String, Integer> assignment) {
        return left.eval(assignment) - right.eval(assignment);
    }

    @Override
    public String toString() {
        return String.format("(%s - %s)", left, right);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Sub sub)) {
            return false;
        }

        return left.equals(sub.left) && right.equals(sub.right);
    }

    @Override
    public int hashCode() {
        return left.hashCode() * 100 + right.hashCode() * 10 + "Sub".hashCode();
    }
}
