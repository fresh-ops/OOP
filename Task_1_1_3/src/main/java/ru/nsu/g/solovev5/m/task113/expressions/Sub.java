package ru.nsu.g.solovev5.m.task113.expressions;

public class Sub extends BinaryOperation {
    public Sub(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public String toString() {
        return String.format("(%s - %s)", left, right);
    }
}
