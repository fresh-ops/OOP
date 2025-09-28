package ru.nsu.g.solovev5.m.task113.expressions;

import java.util.Map;

/**
 * An addition operation.
 */
public class Add extends BinaryOperation {
    /**
     * Constructs a new addition operation.
     *
     * @param left  a left operand
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
    public Expression simplify() {
        var leftSimplified = left.simplify();
        var rightSimplified = right.simplify();

        if (leftSimplified instanceof Number a
            && rightSimplified instanceof Number b) {
            return new Number(a.value + b.value);
        } else if (leftSimplified instanceof Number number
            && number.value == 0) {
            return rightSimplified;
        } else if (rightSimplified instanceof Number number
            && number.value == 0) {
            return leftSimplified;
        }

        return new Add(leftSimplified, rightSimplified);
    }

    @Override
    protected int eval(Map<String, Integer> assignment) {
        return left.eval(assignment) + right.eval(assignment);
    }

    @Override
    public String toString() {
        return String.format("(%s + %s)", left, right);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Add add)) {
            return false;
        }

        return left.equals(add.left) && right.equals(add.right);
    }

    @Override
    public int hashCode() {
        return left.hashCode() * 100 + right.hashCode() * 10 + "Add".hashCode();
    }
}
