package ru.nsu.g.solovev5.m.task113.expressions;

public class Number extends Expression {
    private final int value;

    public Number(int value) {
        super();
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
