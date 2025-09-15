package ru.nsu.g.solovev5.m.task113.expressions;

public class Variable extends Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
