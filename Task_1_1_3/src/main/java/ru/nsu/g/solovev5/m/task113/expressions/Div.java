package ru.nsu.g.solovev5.m.task113.expressions;

public class Div extends BinaryOperation {
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
    public String toString() {
        return String.format("(%s / %s)", left, right);
    }
}
