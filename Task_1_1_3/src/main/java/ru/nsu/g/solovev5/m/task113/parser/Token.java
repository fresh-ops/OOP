package ru.nsu.g.solovev5.m.task113.parser;

/**
 * A token. Consists of a type and a string value.
 *
 * @param type a token type
 * @param value a string covered by this token
 */
public record Token(TokenType type, String value) {
}
