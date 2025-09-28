package ru.nsu.g.solovev5.m.task113.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.stream.Stream;

class LexerTest {
    @ParameterizedTest
    @MethodSource
    void checkCurrent(String input, Token expected) {
        var lexer = new Lexer(input);

        assertEquals(expected, lexer.current());
        assertEquals(expected, lexer.current());
    }

    private static Stream<Arguments> checkCurrent() {
        return Stream.of(
                Arguments.of("()", new Token(TokenType.LEFT_PARENTHESIS, "(")),
                Arguments.of("1234abc", new Token(TokenType.NUMBER, "1234")),
                Arguments.of("", new Token(TokenType.EOF, "")),
                Arguments.of(")asdlfkj", new Token(TokenType.RIGHT_PARENTHESIS, ")")),
                Arguments.of("+0", new Token(TokenType.OPERATOR, "+"))
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkNext(String input, Token first, Token second) {
        var lexer = new Lexer(input);

        assertEquals(first, lexer.next());
        assertEquals(second, lexer.next());
    }

    private static Stream<Arguments> checkNext() {
        return Stream.of(
                Arguments.of("()", new Token(TokenType.LEFT_PARENTHESIS, "("),
                        new Token(TokenType.RIGHT_PARENTHESIS, ")")),
                Arguments.of("  1234abc", new Token(TokenType.NUMBER, "1234"),
                        new Token(TokenType.VARIABLE, "abc")),
                Arguments.of(" *", new Token(TokenType.OPERATOR, "*"),
                        new Token(TokenType.EOF, "")),
                Arguments.of("(    707+42)", new Token(TokenType.LEFT_PARENTHESIS, "("),
                        new Token(TokenType.NUMBER, "707")),
                Arguments.of("abc/0", new Token(TokenType.VARIABLE, "abc"),
                        new Token(TokenType.OPERATOR, "/"))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"@", "^", ";", "#1234", "_abc"})
    void checkUnresolvedSymbol(String input) {
        var lexer = new Lexer(input);

        assertThrows(RuntimeException.class, lexer::next);
    }
}