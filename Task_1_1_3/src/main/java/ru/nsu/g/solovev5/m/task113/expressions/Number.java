package ru.nsu.g.solovev5.m.task113.expressions;

import java.util.HashMap;

/**
 * Represents an integer number.
 */
public class Number extends Expression {
    private final int value;

    /**
     * Constructs a new number of given value.
     *
     * @param value a value of this number
     */
    public Number(int value) {
        super();
        this.value = value;
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    @Override
    protected int eval(HashMap<String, Integer> assignment) {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
