package ru.nsu.g.solovev5.m.task113.expressions;

import java.util.Map;
import java.util.Objects;

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
    protected int eval(Map<String, Integer> assignment) {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Number number)) return false;
        return value == number.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
