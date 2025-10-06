package ru.nsu.g.solovev5.m.task113.parser;

public class UnsupportedOperatorException extends ParserException {
    public UnsupportedOperatorException(String operator) {
        super(String.format("Unsupported operator \"%s\"", operator));
    }
}
