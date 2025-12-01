package ru.nsu.g.solovev5.m.gradebook.semester.subject.exceptions;

import ru.nsu.g.solovev5.m.gradebook.semester.subject.AssessmentType;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.grades.Grade;

/**
 * Occurred when there is a mismatch between an assessment type and a grade.
 */
public class IllegalGradeException extends IllegalArgumentException {
    /**
     * Creates a new IllegalGradeException.
     *
     * @param type an assessment type
     * @param grade a mismatched grade
     */
    public IllegalGradeException(AssessmentType type, Grade grade) {
        super(
            "The assessment type " + type
                + " cannot have a grade " + grade
        );
    }
}
