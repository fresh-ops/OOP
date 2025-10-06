package ru.nsu.g.solovev5.m.task113.parser;

public class UnexpectedTokenException extends ParserException {
    public UnexpectedTokenException(Token got, TokenType expected) {
        super(String.format("Unexpected token %s, expected %s",
            got,
            expected
        ));
    }

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
