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
}
