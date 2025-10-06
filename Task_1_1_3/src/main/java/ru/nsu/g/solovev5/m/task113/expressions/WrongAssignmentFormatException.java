package ru.nsu.g.solovev5.m.task113.expressions;

public class WrongAssignmentFormatException extends EvaluationException {
    public WrongAssignmentFormatException(String entry) {
        super("Assignment error in \"" + entry + "\"");
    }
}
