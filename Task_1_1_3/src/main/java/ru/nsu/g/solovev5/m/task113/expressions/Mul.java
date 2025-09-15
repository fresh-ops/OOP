package ru.nsu.g.solovev5.m.task113.expressions;

public class Mul extends BinaryOperation {
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
    public String toString() {
        return String.format("(%s * %s)", left, right);
    }
}
