package ru.nsu.g.solovev5.m.gradebook.semester.subject.grades;

/**
 * A university grade.
 *
 * <p>
 * As a grade should be either differentiated or pass/fail the interface permits
 * only 2 realization.
 * </p>
 */
public sealed interface Grade permits DifferentiatedGrade, CreditGrade {
    /**
     * Returns the name of this grade, that should be written in a credit book.
     *
     * @return the name of this grade
     */
    String getName();

    /**
     * Determines whether this grade is positive(i.e. the student can continue his studies) or not.
     *
     * @return {@code true} if this is positive, {@code false} otherwise
     */
    boolean isPositive();

    /**
     * Determines whether this grade is differentiated or not.
     *
     * @return {@code true} if this grade is differentiated, {@code false} otherwise
     */
    boolean isDifferentiated();

    /**
     * Returns the numeric value of a differentiated grade. If the grade is not differentiated
     * throws an UnsupportedOperationException.
     *
     * @return the numeric value of this grade
     * @throws UnsupportedOperationException if this grade is not differentiated
     */
    default int getNumeric() {
        throw new UnsupportedOperationException();
    }
}
