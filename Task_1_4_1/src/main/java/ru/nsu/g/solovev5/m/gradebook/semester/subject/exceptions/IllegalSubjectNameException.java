package ru.nsu.g.solovev5.m.gradebook.semester.subject.exceptions;

/**
 * Occurred when the subject has an invalid name.
 */
public class IllegalSubjectNameException extends RuntimeException {
    /**
     * Creates a new IllegalSubjectNameException.
     *
     * @param name an invalid subjects name
     */
    public IllegalSubjectNameException(String name) {
        super("The " + name + " is not a valid subject name");
    }

    /**
     * Creates a new IllegalSubjectNameException.
     *
     * @param name an invalid subjects name
     * @param cause a cause exception
     */
    public IllegalSubjectNameException(String name, Throwable cause) {
        super("The " + name + " is not a valid subject name", cause);
    }
}
