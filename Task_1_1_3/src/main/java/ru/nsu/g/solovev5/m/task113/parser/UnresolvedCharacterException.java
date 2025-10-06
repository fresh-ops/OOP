package ru.nsu.g.solovev5.m.task113.parser;

public class UnresolvedCharacterException extends RuntimeException {
    public UnresolvedCharacterException(char character, int position) {
        super(String.format("Unresolved character \"%c\" at position %d",
            character,
            position
        ));
    }
}
