package ru.nsu.g.solovev5.m.gradebook.semester.exceptions;

/**
 * Occurred when a semester has a number that exits the bounds.
 */
public class IllegalSemesterNumberException extends IllegalArgumentException {
    /**
     * Creates a new IllegalSemesterNumberException.
     *
     * @param number the number that exits the bounds
     */
    public IllegalSemesterNumberException(int number) {
        super("The number " + number + " is out of the semester number bounds");
    }
}
