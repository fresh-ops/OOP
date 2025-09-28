package ru.nsu.g.solovev5.m.task113.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class VariableTest {
    @ParameterizedTest
    @ValueSource(strings = {"x", "y", "WORD", "alpha1", "delta_2"})
    void checkToString(String name) {
        var variable = new Variable(name);

        assertEquals(name, variable.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"x", "y", "WORD", "alpha1", "delta_2"})
    void checkPrint(String name) {
        var bytes = new ByteArrayOutputStream();
        var stream = new PrintStream(bytes);

        var variable = new Variable(name);
        variable.print(stream);
        stream.close();

        assertEquals(name + '\n', bytes.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "x,x,1",
            "x,y,0",
            "y, y, 1",
            "my_best_variable,helloWorld,0",
            "z1,z1,1"
    })
    void checkDerivative(String name, String differential, int expected) {
        var variable = new Variable(name);

        assertEquals(new Number(expected), variable.derivative(differential));
    }

    @ParameterizedTest
    @CsvSource({
            "x,x=13;y=10,13",
            "x,x=0,0",
            "y,x=4;y=89,89",
            "my_best_variable,x=12;z=987;my_best_variable=99;test=37,99",
            "z1,z0=0;z1=1;z2=2;z3=3,1"
    })
    void checkEval(String name, String assignments, int expected) {
        var variable = new Variable(name);

        assertEquals(expected, variable.eval(assignments));
    }

    @ParameterizedTest
    @CsvSource({
            "x,xx=12",
            "x,y=0",
            "test,tEst=13",
            "var,VaR=1234",
            "x,xyz=0"
    })
    void checkEvalWithMissingAssignment(String name, String assignments) {
        var variable = new Variable(name);

        assertThrows(RuntimeException.class, () -> variable.eval(assignments));
    }

    @ParameterizedTest
    @ValueSource(strings = {"x", "Hello", "abc_123_cde", "TeStInG"})
    void checkEqualsAndHashCode(String variable) {
        var variable1 = new Variable(variable);
        var variable2 = new Variable(variable);

        assertEquals(variable1, variable1);
        assertEquals(variable1.hashCode(), variable1.hashCode());
        assertEquals(variable1, variable2);
        assertEquals(variable1.hashCode(), variable2.hashCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"x", "Hello", "abc_123_cde", "TeStInG"})
    void checkNotEqualsAndHashCode(String variable) {
        var variable1 = new Variable(variable);
        var variable2 = new Variable(variable + 99);

        assertNotEquals(variable1, variable2);
        assertNotEquals(variable1.hashCode(), variable2.hashCode());
    }
}