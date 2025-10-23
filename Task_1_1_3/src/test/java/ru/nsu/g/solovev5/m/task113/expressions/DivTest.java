package ru.nsu.g.solovev5.m.task113.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DivTest {
    @ParameterizedTest
    @MethodSource
    void checkDerivative(Expression left, Expression right, String variable) {
        var div = new Div(left, right);
        var leftDerivative = left.derivative(variable);
        var rightDerivative = right.derivative(variable);
        var expected = new Div(
            new Sub(
                new Mul(right, leftDerivative),
                new Mul(left, rightDerivative)
            ),
            new Mul(right, right)
        );

        assertEquals(expected, div.derivative(variable));
    }

    private static Stream<Arguments> checkDerivative() {
        return Stream.of(
            Arguments.of(new Number(1), new Variable("x"), "x"),
            Arguments.of(new Variable("x"), new Variable("x"), "x"),
            Arguments.of(new Variable("hello"), new Variable("world"), "world")
        );
    }

    @ParameterizedTest
    @MethodSource("dataEval")
    void checkSimplifyAndEval(Expression left, Expression right, String assignment) {
        var div = new Div(left, right);
        var simplified = div.simplify();

        assertEquals(div.eval(assignment), simplified.eval(assignment));
        assertEquals(simplified, simplified.simplify());
    }

    @ParameterizedTest
    @MethodSource("dataEval")
    void checkEval(Expression left, Expression right, String assignment) {
        var div = new Div(left, right);
        var expected = left.eval(assignment) / right.eval(assignment);

        assertEquals(expected, div.eval(assignment));
    }

    private static Stream<Arguments> dataEval() {
        return Stream.of(
            Arguments.of(new Number(1), new Number(1), "x=0"),
            Arguments.of(new Number(123), new Number(321), "y=1"),
            Arguments.of(new Variable("x"), new Number(42), "x=42"),
            Arguments.of(new Variable("hello"),
                new Variable("world"),
                "hello=23;world=46"
            ),
            Arguments.of(new Div(
                    new Variable("X"), new Variable("Y")
                ),
                new Number(3),
                "X=1234;Y=456;Z=1"
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkToString(Expression left, Expression right, String expected) {
        var div = new Div(left, right);

        assertEquals(expected, div.toString());
    }

    private static Stream<Arguments> checkToString() {
        return Stream.of(
            Arguments.of(new Number(1), new Number(1), "(1 / 1)"),
            Arguments.of(new Number(123), new Number(321), "(123 / 321)"),
            Arguments.of(new Variable("x"), new Number(0), "(x / 0)"),
            Arguments.of(new Variable("hello"),
                new Variable("world"),
                "(hello / world)"
            ),
            Arguments.of(new Div(new Number(1), new Number(2)),
                new Number(3),
                "((1 / 2) / 3)"
            )
        );
    }

    @ParameterizedTest
    @MethodSource("equalsAndHashCode")
    void checkEqualsAndHashCode(Expression left, Expression right) {
        var div1 = new Div(left, right);
        assertEquals(div1, div1);
        assertEquals(div1.hashCode(), div1.hashCode());

        var div2 = new Div(left, right);
        assertEquals(div1, div2);
        assertEquals(div1.hashCode(), div2.hashCode());
    }

    @ParameterizedTest
    @MethodSource("equalsAndHashCode")
    void checkNotEqualsAndHashCode(Expression left, Expression right) {
        var div1 = new Div(left, right);
        var div2 = new Div(new Number(0), new Number(1));

        assertNotEquals(div1, div2);
        assertNotEquals(div1.hashCode(), div2.hashCode());

        var div3 = new Div(left, left);
        assertNotEquals(div1, div3);
        assertNotEquals(div1.hashCode(), div3.hashCode());

        var div4 = new Div(right, right);
        assertNotEquals(div1, div4);
        assertNotEquals(div1.hashCode(), div4.hashCode());

        var div5 = new Div(right, left);
        assertNotEquals(div1, div5);
        assertNotEquals(div1.hashCode(), div5.hashCode());
    }

    private static Stream<Arguments> equalsAndHashCode() {
        return Stream.of(
            Arguments.of(new Number(1), new Number(7)),
            Arguments.of(new Number(123), new Number(321)),
            Arguments.of(new Variable("x"), new Number(0)),
            Arguments.of(new Variable("hello"), new Variable("world")),
            Arguments.of(new Div(new Variable("X"), new Variable("Y")),
                new Number(3)
            )
        );
    }
}