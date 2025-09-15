package ru.nsu.g.solovev5.m.task113.expressions;

public class Div extends BinaryOperation {
    public Div(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("(%s / %s)", left, right);
    }
}
