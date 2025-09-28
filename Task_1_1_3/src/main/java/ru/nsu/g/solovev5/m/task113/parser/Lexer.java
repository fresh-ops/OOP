package ru.nsu.g.solovev5.m.task113.parser;

import java.util.regex.Pattern;

/**
 * A lexer class. Provides methods to tokenize a given input string.
 */
class Lexer {
    private int position;
    private final String input;
    private Token token;

    /**
     * Constructs a new lexer.
     *
     * @param input a string to split into tokens
     */
    public Lexer(String input) {
        this.input = input;
        position = 0;
    }

    /**
     * Returns current token. Does not change position.
     *
     * @return a token read from current position
     */
    public Token current() {
        return token == null ? next() : token;
    }

    /**
     * Returns a next token. Changes position up to next token start.
     *
     * @return a new token
     */
    public Token next() {
        token = read();

        return token;
    }

    private Token read() {
        for (var type : TokenType.values()) {
            var pattern = Pattern.compile(type.pattern);
            var matcher = pattern.matcher(input.substring(position));

            if (matcher.lookingAt()) {
                var matched = matcher.group();

                position += matched.length();
                if (type == TokenType.SPACE) {
                    return read();
                }
                return new Token(type, matched);
            }
        }

        throw new RuntimeException("Unresolved symbol \"" +
            input.charAt(position) + "\" at position " +
            position
        );
    }
}
