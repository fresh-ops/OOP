package ru.nsu.g.solovev5.m.gradebook.semester.subject.exceptions;

/**
 * Occurred when the subject has a blank name.
 */
public class BlankSubjectNameException extends IllegalSubjectNameException {
    /**
     * Creates a new BlankSubjectNameException.
     */
    public BlankSubjectNameException() {
        super("blank string");
    }
}
