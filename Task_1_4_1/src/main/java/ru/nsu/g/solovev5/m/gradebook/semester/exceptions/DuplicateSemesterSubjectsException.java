package ru.nsu.g.solovev5.m.gradebook.semester.exceptions;

/**
 * Occurred when creating a semester with subject duplicates.
 */
public class DuplicateSemesterSubjectsException extends IllegalSemesterSubjectsException {
    /**
     * Creates a new DuplicateSemesterSubjectsException.
     */
    public DuplicateSemesterSubjectsException() {
        super("The semester cannot contain duplicate subjects");
    }

    /**
     * Creates a new DuplicateSemesterSubjectsException.
     *
     * @param cause a cause exception
     */
    public DuplicateSemesterSubjectsException(Throwable cause) {
        super("The semester cannot contain duplicate subjects", cause);
    }
}
