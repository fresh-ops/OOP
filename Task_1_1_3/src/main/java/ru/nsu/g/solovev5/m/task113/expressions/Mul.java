package ru.nsu.g.solovev5.m.task113.expressions;

import java.util.Map;

/**
 * A multiplication operation.
 */
public class Mul extends BinaryOperation {
    /**
     * Constructs a new multiplication operation.
     *
     * @param left  a left operand
     * @param right a right operand
     */
    public Mul(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Expression derivative(String variable) {
        var firstPart = new Mul(left, right.derivative(variable));
        var secondPart = new Mul(right, left.derivative(variable));

        return new Add(firstPart, secondPart);
    }

    @Override
    public Expression simplify() {
        var leftSimplified = left.simplify();
        var rightSimplified = right.simplify();

        if (leftSimplified instanceof Number a
                && rightSimplified instanceof Number b) {
            return new Number(a.value * b.value);
        } else if (leftSimplified.equals(new Number(0))
            || rightSimplified.equals(new Number(1))) {
            return leftSimplified;
        } else if (leftSimplified.equals(new Number(1))
            || rightSimplified.equals(new Number(0))) {
            return rightSimplified;
        }

        return new Mul(leftSimplified, rightSimplified);
    }

    @Override
    protected int eval(Map<String, Integer> assignment) {
        return left.eval(assignment) * right.eval(assignment);
    }

    @Override
    public String toString() {
        return String.format("(%s * %s)", left, right);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Mul mul)) {
            return false;
        }

        return left.equals(mul.left) && right.equals(mul.right);
    }

    @Override
    public int hashCode() {
        return left.hashCode() * 100 + right.hashCode() * 10 + "Mul".hashCode();
    }
}
