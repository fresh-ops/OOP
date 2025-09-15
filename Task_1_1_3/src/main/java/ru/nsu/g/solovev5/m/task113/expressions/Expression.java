package ru.nsu.g.solovev5.m.task113.expressions;

import java.io.PrintStream;

public abstract class Expression {
    public void print(PrintStream out) {
        out.println(this);
    }

    public abstract Expression derivative(String variable);
}
