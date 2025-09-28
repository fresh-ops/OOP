package ru.nsu.g.solovev5.m.task113.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AddTest {
    @ParameterizedTest
    @MethodSource
    void checkDerivative(Expression left, Expression right, String variable) {
        var add = new Add(left, right);
        var expected = new Add(left.derivative(variable), right.derivative(variable));

        assertEquals(expected, add.derivative(variable));
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
            Arguments.of(new Add(new Variable("X"), new Variable("Y")),
                new Number(3),
                "Y"
            )
        );
    }

    @ParameterizedTest
    @MethodSource("dataEval")
    void checkSimplifyAndEval(Expression left, Expression right, String assignment) {
        var add = new Add(left, right);
        var simplified = add.simplify();

        assertEquals(add.eval(assignment), simplified.eval(assignment));
        assertEquals(simplified, simplified.simplify());
    }

    @ParameterizedTest
    @MethodSource("dataEval")
    void checkEval(Expression left, Expression right, String assignment) {
        var add = new Add(left, right);
        var expected = left.eval(assignment) + right.eval(assignment);

        assertEquals(expected, add.eval(assignment));
    }

    private static Stream<Arguments> dataEval() {
        return Stream.of(
            Arguments.of(new Number(1), new Number(1), "x=0"),
            Arguments.of(new Number(123), new Number(321), "y=1"),
            Arguments.of(new Variable("x"), new Number(0), "x=42"),
            Arguments.of(new Number(0), new Variable("y"), "y=14"),
            Arguments.of(new Variable("hello"),
                new Variable("world"),
                "hello=23;world=46"
            ),
            Arguments.of(new Add(new Variable("X"), new Variable("Y")),
                new Number(3),
                "X=1234;Y=456;Z=1"
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkToString(Expression left, Expression right, String expected) {
        var add = new Add(left, right);

        assertEquals(expected, add.toString());
    }

    private static Stream<Arguments> checkToString() {
        return Stream.of(
            Arguments.of(new Number(1), new Number(1), "(1 + 1)"),
            Arguments.of(new Number(123), new Number(321), "(123 + 321)"),
            Arguments.of(new Variable("x"), new Number(0), "(x + 0)"),
            Arguments.of(new Variable("hello"), new Variable("world"),
                "(hello + world)"
            ),
            Arguments.of(new Add(new Number(1), new Number(2)),
                new Number(3),
                "((1 + 2) + 3)"
            )
        );
    }

    @ParameterizedTest
    @MethodSource("equalsAndHashCode")
    void checkEqualsAndHashCode(Expression left, Expression right) {
        var add1 = new Add(left, right);

        assertEquals(add1, add1);
        assertEquals(add1.hashCode(), add1.hashCode());

        var add2 = new Add(left, right);
        assertEquals(add1, add2);
        assertEquals(add1.hashCode(), add2.hashCode());
    }

    @ParameterizedTest
    @MethodSource("equalsAndHashCode")
    void checkNotEqualsAndHashCode(Expression left, Expression right) {
        var add1 = new Add(left, right);
        var add2 = new Add(new Number(0), new Number(1));

        assertNotEquals(add1, add2);
        assertNotEquals(add1.hashCode(), add2.hashCode());

        var add3 = new Add(left, left);
        assertNotEquals(add1, add3);
        assertNotEquals(add1.hashCode(), add3.hashCode());

        var add4 = new Add(right, right);
        assertNotEquals(add1, add4);
        assertNotEquals(add1.hashCode(), add4.hashCode());

        var add5 = new Add(right, left);
        assertNotEquals(add1, add5);
        assertNotEquals(add1.hashCode(), add5.hashCode());
    }

    private static Stream<Arguments> equalsAndHashCode() {
        return Stream.of(
            Arguments.of(new Number(1), new Number(7)),
            Arguments.of(new Number(123), new Number(321)),
            Arguments.of(new Variable("x"), new Number(0)),
            Arguments.of(new Variable("hello"), new Variable("world")),
            Arguments.of(new Add(new Variable("X"), new Variable("Y")),
                new Number(3)
            )
        );
    }
}