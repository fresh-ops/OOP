package ru.nsu.g.solovev5.m.task113.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class SubTest {
    @ParameterizedTest
    @MethodSource
    void checkDerivative(Expression left, Expression right, String variable) {
        var sub = new Sub(left, right);
        var expected = new Sub(left.derivative(variable), right.derivative(variable));

        assertEquals(expected, sub.derivative(variable));
    }

    private static Stream<Arguments> checkDerivative() {
        return Stream.of(
                Arguments.of(new Number(1), new Number(1), "x"),
                Arguments.of(new Number(123), new Number(321), "y"),
                Arguments.of(new Variable("x"), new Number(0), "x"),
                Arguments.of(new Variable("hello"), new Variable("world"), "world"),
                Arguments.of(new Sub(new Variable("X"), new Variable("Y")), new Number(3), "Y")
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkEval(Expression left, Expression right, String assignment) {
        var sub = new Sub(left, right);
        var expected = left.eval(assignment) - right.eval(assignment);

        assertEquals(expected, sub.eval(assignment));
    }

    private static Stream<Arguments> checkEval() {
        return Stream.of(
                Arguments.of(new Number(1), new Number(1), "x=0"),
                Arguments.of(new Number(123), new Number(321), "y=1"),
                Arguments.of(new Variable("x"), new Number(0), "x=42"),
                Arguments.of(new Variable("hello"), new Variable("world"), "hello=23;world=46"),
                Arguments.of(new Sub(new Variable("X"), new Variable("Y")), new Number(3), "X=1234;Y=456;Z=1")
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkToString(Expression left, Expression right, String expected) {
        var sub = new Sub(left, right);

        assertEquals(expected, sub.toString());
    }

    private static Stream<Arguments> checkToString() {
        return Stream.of(
                Arguments.of(new Number(1), new Number(1), "(1 - 1)"),
                Arguments.of(new Number(123), new Number(321), "(123 - 321)"),
                Arguments.of(new Variable("x"), new Number(0), "(x - 0)"),
                Arguments.of(new Variable("hello"), new Variable("world"), "(hello - world)"),
                Arguments.of(new Sub(new Number(1), new Number(2)), new Number(3), "((1 - 2) - 3)")
        );
    }

    @ParameterizedTest
    @MethodSource("equalsAndHashCode")
    void checkEqualsAndHashCode(Expression left, Expression right) {
        var sub1 = new Sub(left, right);
        var sub2 = new Sub(left, right);

        assertEquals(sub1, sub1);
        assertEquals(sub1.hashCode(), sub1.hashCode());
        assertEquals(sub1, sub2);
        assertEquals(sub1.hashCode(), sub2.hashCode());
    }

    @ParameterizedTest
    @MethodSource("equalsAndHashCode")
    void checkNotEqualsAndHashCode(Expression left, Expression right) {
        var sub1 = new Sub(left, right);
        var sub2 = new Sub(new Number(0), new Number(1));
        var sub3 = new Sub(left, left);
        var sub4 = new Sub(right, right);
        var sub5 = new Sub(right, left);

        assertNotEquals(sub1, sub2);
        assertNotEquals(sub1.hashCode(), sub2.hashCode());
        assertNotEquals(sub1, sub3);
        assertNotEquals(sub1.hashCode(), sub3.hashCode());
        assertNotEquals(sub1, sub4);
        assertNotEquals(sub1.hashCode(), sub4.hashCode());
        assertNotEquals(sub1, sub5);
        assertNotEquals(sub1.hashCode(), sub5.hashCode());
    }

    private static Stream<Arguments> equalsAndHashCode() {
        return Stream.of(
                Arguments.of(new Number(1), new Number(7)),
                Arguments.of(new Number(123), new Number(321)),
                Arguments.of(new Variable("x"), new Number(0)),
                Arguments.of(new Variable("hello"), new Variable("world")),
                Arguments.of(new Sub(new Variable("X"), new Variable("Y")), new Number(3))
        );
    }
}