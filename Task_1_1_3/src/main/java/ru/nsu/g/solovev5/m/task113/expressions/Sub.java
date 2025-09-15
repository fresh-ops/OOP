package ru.nsu.g.solovev5.m.task113.expressions;

public class Sub extends BinaryOperation {
    public Sub(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("(%s - %s)", left, right);
    }
}
