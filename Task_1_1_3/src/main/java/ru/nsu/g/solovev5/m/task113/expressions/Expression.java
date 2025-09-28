package ru.nsu.g.solovev5.m.task113.expressions;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * An abstract expression. Provides methods to print this expression,
 * derivative and evaluate it.
 */
public abstract class Expression {
    /**
     * Prints this expression to the given stream.
     *
     * @param out the stream where to print expression
     */
    public void print(PrintStream out) {
        out.println(this);
    }

    /**
     * Derivatives this expression by given variable.
     *
     * @param variable a variable to derivative by
     * @return a new derivative expression
     */
    public abstract Expression derivative(String variable);

    /**
     * Evaluates this expression. Accepts a string in form of
     * {@code "a = 1; b = 2}. If there are not enough variables or
     * a wrong format, throws a RuntimeException.
     *
     * @param assignments a string with variable assignments
     * @return a result of evaluation
     */
    public int eval(String assignments) {
        var entries = assignments.split("\\s*;\\s*");
        var map = new HashMap<String, Integer>();

        for (var entry : entries) {
            var pair = entry.split("\\s*=\\s*");
            if (pair.length != 2) {
                throw new RuntimeException("Assignment error in \"" + entry + "\"");
            }

            map.put(pair[0], Integer.parseInt(pair[1]));
        }

        return eval(map);
    }

    protected abstract int eval(Map<String, Integer> assignment);

    /**
     * Simplifies this expression. Returns a new instance.
     *
     * @return a new simplified expression
     */
    public abstract Expression simplify();
}
