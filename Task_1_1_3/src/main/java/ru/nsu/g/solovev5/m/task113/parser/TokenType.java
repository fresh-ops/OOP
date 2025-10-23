package ru.nsu.g.solovev5.m.task113.parser;

/**
 * A token types. All types have its own regular expression to parse it.
 */
public enum TokenType {
    LEFT_PARENTHESIS("\\(", "\"(\""), RIGHT_PARENTHESIS("\\)", "\")\""),
    NUMBER("\\d+", "number"), VARIABLE("[a-zA-Z]\\w*", "variable"),
    OPERATOR("[\\*/+-]", "operator"), SPACE("\\s+"),
    EOF("$", "EOF");

    final String pattern;
    final String name;

    TokenType(String pattern, String name) {
        this.pattern = pattern;
        this.name = name;
    }

    TokenType(String pattern) {
        this(pattern, '"' + pattern + '"');
    }

    @Override
    public String toString() {
        return name;
    }
}
