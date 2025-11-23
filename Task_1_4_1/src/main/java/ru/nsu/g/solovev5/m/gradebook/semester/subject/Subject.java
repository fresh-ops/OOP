package ru.nsu.g.solovev5.m.gradebook.semester.subject;

import java.util.Objects;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.exceptions.BlankSubjectNameException;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.exceptions.IllegalGradeException;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.exceptions.IllegalSubjectNameException;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.grades.CreditGrade;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.grades.DifferentiatedGrade;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.grades.Grade;

/**
 * Represents a study subject.
 */
public class Subject {
    public final String name;
    public final AssessmentType type;
    public final Grade grade;

    private Subject(String name, AssessmentType type, Grade grade) {
        this.name = name;
        this.type = type;
        this.grade = grade;
    }

    /**
     * Creates a new study subject with given parameters. Before creating asserts if the grade
     * corresponds to the assessment type.
     *
     * @param name the name of this subject. Should not be null or blank
     * @param type the assessment type of this subject
     * @param grade the subjects score. Must match the assessment type
     * @return a new Subject
     * @throws IllegalGradeException if the given grade does not correspond to the assessment type
     * @throws IllegalSubjectNameException if the name is not valid
     */
    public static Subject of(String name, AssessmentType type, DifferentiatedGrade grade) {
        validateName(name);
        if (!type.isDifferentiated()) {
            throw new IllegalGradeException(type, grade);
        }

        return new Subject(name, type, grade);
    }

    /**
     * Creates a new study subject with given parameters. Before creating asserts if the grade
     * corresponds to the assessment type.
     *
     * @param name the name of this subject. Should not be null or blank
     * @param type the assessment type of this subject
     * @param grade the subjects score. Must match the assessment type
     * @return a new Subject
     * @throws IllegalGradeException if the given grade does not correspond to the assessment type
     * @throws IllegalSubjectNameException if the name is not valid
     */
    public static Subject of(String name, AssessmentType type, CreditGrade grade) {
        validateName(name);
        if (type.isDifferentiated()) {
            throw new IllegalGradeException(type, grade);
        }

        return new Subject(name, type, grade);
    }

    private static void validateName(String name) {
        if (name == null) {
            throw new IllegalSubjectNameException(name);
        }

        if (name.isBlank()) {
            throw new BlankSubjectNameException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Subject subject)) {
            return false;
        }

        return Objects.equals(name, subject.name)
            && type == subject.type
            && Objects.equals(grade, subject.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, grade);
    }
}
