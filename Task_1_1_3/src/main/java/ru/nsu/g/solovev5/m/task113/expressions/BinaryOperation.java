package ru.nsu.g.solovev5.m.task113.expressions;

/**
 * An abstract binary operation. Accepts two expressions.
 */
public abstract class BinaryOperation extends Expression {
    protected final Expression left;
    protected final Expression right;

    /**
     * Constructs a new binary operation.
     *
     * @param left  a left operand
     * @param right a right operand
     */
    public BinaryOperation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
}
