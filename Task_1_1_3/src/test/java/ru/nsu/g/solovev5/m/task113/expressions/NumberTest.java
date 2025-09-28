package ru.nsu.g.solovev5.m.task113.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NumberTest {
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 20, 10000, 13})
    void checkToString(int value) {
        var number = new Number(value);
        assertEquals(Integer.toString(value), number.toString());
    }

    @ParameterizedTest
    @ValueSource(ints = {123, 87, 29, 48, 433})
    void checkPrint(int value) {
        var bytes = new ByteArrayOutputStream();
        var stream = new PrintStream(bytes);

        var number = new Number(value);
        number.print(stream);
        stream.close();

        assertEquals(Integer.toString(value) + '\n', bytes.toString());
    }

    @ParameterizedTest
    @ValueSource(ints = {48, 208, 984, 1, 0})
    void checkDerivative(int value) {
        var number = new Number(value);

        assertEquals(new Number(0), number.derivative("x"));
    }

    @ParameterizedTest
    @ValueSource(ints = {98, 24, 298, 491, 0})
    void checkEval(int value) {
        var number = new Number(value);

        assertEquals(value, number.eval("x = 0"));
    }

    @ParameterizedTest
    @ValueSource(ints = {123, 298, 0, 87, 1})
    void checkEqualsAndHashCode(int value) {
        var number1 = new Number(value);
        assertEquals(number1, number1);
        assertEquals(number1.hashCode(), number1.hashCode());

        var number2 = new Number(value);
        assertEquals(number1, number2);
        assertEquals(number1.hashCode(), number2.hashCode());
    }

    @ParameterizedTest
    @ValueSource(ints = {123, 298, 0, 87, 1})
    void checkNotEqualsAndHashCode(int value) {
        var number1 = new Number(value);
        var number2 = new Number(value + 99);

        assertNotEquals(number1, number2);
        assertNotEquals(number1.hashCode(), number2.hashCode());
    }
}