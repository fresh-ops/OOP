package ru.nsu.g.solovev5.m.gradebook.exception;

/**
 * Occurred when there is a misorder of semesters in a grade book.
 */
public class WrongSemestersOrderException extends IllegalArgumentException {
    /**
     * Creates a new WrongSemestersOrderException.
     *
     * @param expected the expected semesters number
     * @param actual the actual semesters number
     */
    public WrongSemestersOrderException(int expected, int actual) {
        super("Misordered semester appending. Expected " + expected + ", but found " + actual);
    }
}
