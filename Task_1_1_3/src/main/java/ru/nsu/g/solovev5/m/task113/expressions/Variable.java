package ru.nsu.g.solovev5.m.task113.expressions;

import java.util.Map;
import java.util.Objects;

/**
 * Represents a variable.
 */
public class Variable extends Expression {
    private final String name;

    /**
     * Constructs a new variable with given name.
     *
     * @param name a name of this variable
     */
    public Variable(String name) {
        this.name = name;
    }

    @Override
    public Expression derivative(String variable) {
        if (name.equals(variable)) {
            return new Number(1);
        }

        return new Number(0);
    }

    @Override
    protected int eval(Map<String, Integer> assignment) {
        var result = assignment.get(name);

        if (result == null) {
            throw new RuntimeException("No assignment for variable " + name);
        }

        return result;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Variable variable)) return false;
        return Objects.equals(name, variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
