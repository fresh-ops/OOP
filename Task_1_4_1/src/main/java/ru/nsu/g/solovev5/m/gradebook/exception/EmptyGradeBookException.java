package ru.nsu.g.solovev5.m.gradebook.exception;

/**
 * Occurred when the grade book cannot perform operation due to emptiness.
 */
public class EmptyGradeBookException extends IllegalStateException {
    /**
     * Creates a new EmptyGradeBookException.
     */
    public EmptyGradeBookException() {
        super("The grade book has no differentiated grades yet");
    }
}
