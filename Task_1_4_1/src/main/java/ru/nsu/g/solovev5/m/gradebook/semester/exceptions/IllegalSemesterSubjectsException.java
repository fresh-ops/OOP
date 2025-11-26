package ru.nsu.g.solovev5.m.gradebook.semester.exceptions;

/**
 * Occurred when there are invalid subjects in the semester.
 */
public abstract class IllegalSemesterSubjectsException extends IllegalArgumentException {
    /**
     * Creates a new IllegalSemesterSubjectsException.
     *
     * @param message a detail message
     */
    public IllegalSemesterSubjectsException(String message) {
        super(message);
    }

    /**
     * Creates a new IllegalSemesterSubjectsException.
     *
     * @param message a detail message
     * @param cause a cause exception
     */
    public IllegalSemesterSubjectsException(String message, Throwable cause) {
        super(message, cause);
    }
}
