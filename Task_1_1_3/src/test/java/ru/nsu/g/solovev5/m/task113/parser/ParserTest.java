package ru.nsu.g.solovev5.m.task113.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nsu.g.solovev5.m.task113.expressions.Add;
import ru.nsu.g.solovev5.m.task113.expressions.Div;
import ru.nsu.g.solovev5.m.task113.expressions.Expression;
import ru.nsu.g.solovev5.m.task113.expressions.Mul;
import ru.nsu.g.solovev5.m.task113.expressions.Number;
import ru.nsu.g.solovev5.m.task113.expressions.Sub;
import ru.nsu.g.solovev5.m.task113.expressions.Variable;

class ParserTest {
    @ParameterizedTest
    @MethodSource
    void checkParse(String input, Expression expected) {
        var parser = new Parser(input);

        assertEquals(expected, parser.parse());
    }

    private static Stream<Arguments> checkParse() {
        return Stream.of(
            Arguments.of("42", new Number(42)),
            Arguments.of("((((101))))", new Number(101)),
            Arguments.of("1+x", new Add(new Number(1), new Variable("x"))),
            Arguments.of("x*y*z", new Mul(
                    new Mul(new Variable("x"), new Variable("y")),
                    new Variable("z")
                )
            ),
            Arguments.of(" 2 *  (hello -  world)", new Mul(
                    new Number(2),
                    new Sub(new Variable("hello"), new Variable("world"))
                )
            ),
            Arguments.of("(x - y) / (x + y)", new Div(
                    new Sub(new Variable("x"), new Variable("y")),
                    new Add(new Variable("x"), new Variable("y"))
                )
            )
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"123)", "123+x y", "1 2", "+"})
    void checkUnparsedPart(String input) {
        var parser = new Parser(input);

        assertThrows(RuntimeException.class, parser::parse);
    }

    @ParameterizedTest
    @ValueSource(strings = {"(1234 + 1234", "x + ", "a + (b * c", "(x + (y + z)"})
    void checkUnendedExpression(String input) {
        var parser = new Parser(input);

        assertThrows(RuntimeException.class, parser::parse);
    }
}