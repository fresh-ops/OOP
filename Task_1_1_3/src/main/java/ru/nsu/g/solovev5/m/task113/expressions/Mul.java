package ru.nsu.g.solovev5.m.task113.expressions;

public class Mul extends BinaryOperation {
    public Mul(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("(%s * %s)", left, right);
    }
}
