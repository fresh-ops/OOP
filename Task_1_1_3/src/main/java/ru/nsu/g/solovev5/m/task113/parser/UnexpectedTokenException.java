package ru.nsu.g.solovev5.m.task113.parser;

/**
 * An exception occurred when found an unexpected token.
 */
public class UnexpectedTokenException extends ParserException {
    /**
     * Constructs a new unexpected token exception.
     *
     * @param got a found token
     * @param expected an expected token type
     */
    public UnexpectedTokenException(Token got, TokenType expected) {
        super(String.format("Unexpected token %s, expected %s",
            got,
            expected
        ));
    }

    /**
     * Constructs a new unexpected token exception.
     *
     * @param got a found token
     * @param expected an array of expected token types
     */
    public UnexpectedTokenException(Token got, TokenType[] expected) {
        super(String.format("Unexpected token %s, expected %s",
            got,
            enumerate(expected)
        ));
    }

    private static String enumerate(TokenType[] strings) {
        var result = new StringBuilder();

        for (int i = 0; i < strings.length - 1; i++) {
            result.append(strings[i]);
            if (i < strings.length - 2) {
                result.append(", ");
            }
        }

        result.append(" or ");
        result.append(strings[strings.length - 1]);

        return result.toString();
    }
}
