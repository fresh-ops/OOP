package ru.nsu.g.solovev5.m.task113.parser;

/**
 * An exception occurred when a character is not supported by the lexer.
 */
public class UnresolvedCharacterException extends RuntimeException {
    /**
     * Constructs a new unresolved character exception.
     *
     * @param character an unresolved character
     * @param position a position where the character occurred
     */
    public UnresolvedCharacterException(char character, int position) {
        super(String.format("Unresolved character \"%c\" at position %d",
            character,
            position
        ));
    }
}
