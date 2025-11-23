package ru.nsu.g.solovev5.m.gradebook;

import ru.nsu.g.solovev5.m.gradebook.student.Student;

/**
 * A student grade book class.
 */
public class GradeBook {
    public final Student student;

    /**
     * Creates a new grade book for this student.
     *
     * @param student an owner of this grade book
     */
    public GradeBook(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        final var newline = '\n';
        final var separator = newline + "=".repeat(30) + newline;

        var sb = new StringBuilder();
        sb.append("Зачетная книжка")
            .append(separator);

        sb.append("Студент: ")
            .append(student)
            .append(newline);
        sb.append("Основа обучения: ")
            .append(student.educationBasis().getName());

        return sb.append(separator).toString();
    }
}
