package ru.nsu.g.solovev5.m.task113.parser;

/**
 * A token types. All types have its own regular expression to parse it.
 */
public enum TokenType {
    LEFT_PARENTHESIS("\\("), RIGHT_PARENTHESIS("\\)"),
    NUMBER("\\d+"), VARIABLE("[a-zA-Z]\\w*"),
    OPERATOR("[\\*/+-]"), SPACE("\\s+"),
    EOF("$");

    final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }
}
