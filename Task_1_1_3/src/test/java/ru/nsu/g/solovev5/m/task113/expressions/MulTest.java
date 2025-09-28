package ru.nsu.g.solovev5.m.task113.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MulTest {
    @ParameterizedTest
    @MethodSource
    void checkDerivative(Expression left, Expression right, String variable) {
        var mul = new Mul(left, right);
        var leftDerivative = left.derivative(variable);
        var rightDerivative = right.derivative(variable);
        var expected = new Add(
            new Mul(left, rightDerivative),
            new Mul(right, leftDerivative)
        );

        assertEquals(expected, mul.derivative(variable));
    }

    private static Stream<Arguments> checkDerivative() {
        return Stream.of(
            Arguments.of(new Number(1), new Number(1), "x"),
            Arguments.of(new Number(123), new Number(321), "y"),
            Arguments.of(new Variable("x"), new Number(0), "x"),
            Arguments.of(new Variable("hello"),
                new Variable("world"),
                "world"
            ),
            Arguments.of(new Mul(new Variable("X"), new Variable("Y")),
                new Number(3),
                "Y"
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkEval(Expression left, Expression right, String assignment) {
        var mul = new Mul(left, right);
        var expected = left.eval(assignment) * right.eval(assignment);

        assertEquals(expected, mul.eval(assignment));
    }

    private static Stream<Arguments> checkEval() {
        return Stream.of(
            Arguments.of(new Number(1), new Number(1), "x=0"),
            Arguments.of(new Number(123), new Number(321), "y=1"),
            Arguments.of(new Variable("x"), new Number(0), "x=42"),
            Arguments.of(new Variable("hello"),
                new Variable("world"),
                "hello=23;world=46"
            ),
            Arguments.of(new Mul(new Variable("X"), new Variable("Y")),
                new Number(3),
                "X=1234;Y=456;Z=1"
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkToString(Expression left, Expression right, String expected) {
        var mul = new Mul(left, right);

        assertEquals(expected, mul.toString());
    }

    private static Stream<Arguments> checkToString() {
        return Stream.of(
            Arguments.of(new Number(1), new Number(1), "(1 * 1)"),
            Arguments.of(new Number(123), new Number(321), "(123 * 321)"),
            Arguments.of(new Variable("x"), new Number(0), "(x * 0)"),
            Arguments.of(new Variable("hello"),
                new Variable("world"),
                "(hello * world)"
            ),
            Arguments.of(new Mul(new Number(1), new Number(2)),
                new Number(3),
                "((1 * 2) * 3)"
            )
        );
    }

    @ParameterizedTest
    @MethodSource("equalsAndHashCode")
    void checkEqualsAndHashCode(Expression left, Expression right) {
        var mul1 = new Mul(left, right);
        assertEquals(mul1, mul1);
        assertEquals(mul1.hashCode(), mul1.hashCode());

        var mul2 = new Mul(left, right);
        assertEquals(mul1, mul2);
        assertEquals(mul1.hashCode(), mul2.hashCode());
    }

    @ParameterizedTest
    @MethodSource("equalsAndHashCode")
    void checkNotEqualsAndHashCode(Expression left, Expression right) {
        var mul1 = new Mul(left, right);
        var mul2 = new Mul(new Number(0), new Number(1));

        assertNotEquals(mul1, mul2);
        assertNotEquals(mul1.hashCode(), mul2.hashCode());

        var mul3 = new Mul(left, left);
        assertNotEquals(mul1, mul3);
        assertNotEquals(mul1.hashCode(), mul3.hashCode());

        var mul4 = new Mul(right, right);
        assertNotEquals(mul1, mul4);
        assertNotEquals(mul1.hashCode(), mul4.hashCode());

        var mul5 = new Mul(right, left);
        assertNotEquals(mul1, mul5);
        assertNotEquals(mul1.hashCode(), mul5.hashCode());
    }

    private static Stream<Arguments> equalsAndHashCode() {
        return Stream.of(
            Arguments.of(new Number(1), new Number(7)),
            Arguments.of(new Number(123), new Number(321)),
            Arguments.of(new Variable("x"), new Number(0)),
            Arguments.of(new Variable("hello"), new Variable("world")),
            Arguments.of(new Mul(new Variable("X"), new Variable("Y")),
                new Number(3)
            )
        );
    }
}