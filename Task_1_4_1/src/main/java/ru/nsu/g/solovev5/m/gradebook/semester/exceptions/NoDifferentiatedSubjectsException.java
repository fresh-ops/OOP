package ru.nsu.g.solovev5.m.gradebook.semester.exceptions;

/**
 * Occurred when the semester does not have differentiated subjects.
 */
public class NoDifferentiatedSubjectsException extends IllegalStateException {
    /**
     * Creates a new NoDifferentiatedSubjectsException.
     *
     * @param number the number of erroneous semester
     */
    public NoDifferentiatedSubjectsException(int number) {
        super("The semester number " + number + " has no differentiated subjects");
    }
}
