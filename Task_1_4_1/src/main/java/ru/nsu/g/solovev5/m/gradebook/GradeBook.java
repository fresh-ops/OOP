package ru.nsu.g.solovev5.m.gradebook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import ru.nsu.g.solovev5.m.gradebook.exception.EmptyGradeBookException;
import ru.nsu.g.solovev5.m.gradebook.exception.WrongSemestersOrderException;
import ru.nsu.g.solovev5.m.gradebook.semester.Semester;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.AssessmentType;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.Subject;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.grades.DifferentiatedGrade;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.grades.Grade;
import ru.nsu.g.solovev5.m.gradebook.student.EducationBasis;
import ru.nsu.g.solovev5.m.gradebook.student.Student;

/**
 * A student grade book class.
 */
public class GradeBook {
    private final int supplementSubjectsCount;
    private final Student student;
    private final List<Semester> semesters;

    /**
     * Creates a new grade book for this student.
     *
     * @param student an owner of this grade book
     */
    public GradeBook(Student student, int supplementSubjectsCount) {
        this.supplementSubjectsCount = supplementSubjectsCount;
        this.student = student;
        this.semesters = new ArrayList<>();
    }

    /**
     * Adds a new semester to this grade book. The semester must follow the chronological order,
     * i.e. if this grade book already has <i>n</i> semesters, the new one should have the
     * <i>n + 1</i> number.
     *
     * @param semester the new semester
     * @throws WrongSemestersOrderException if the semester does not follow the order
     */
    public void add(Semester semester) {
        if (semester.getNumber() != semesters.size() + 1) {
            throw new WrongSemestersOrderException(semesters.size() + 1, semester.getNumber());
        }

        semesters.add(semester);
    }

    /**
     * Calculates the average of all differentiated grades in this grade book.
     *
     * @return the average of all differentiated grades
     */
    public OptionalDouble average() {
        return semesters.stream()
            .flatMap(Semester::stream)
            .filter(subject -> subject.getType().isDifferentiated())
            .mapToDouble(subject -> subject.getGrade().getNumeric())
            .average();
    }

    /**
     * Determines if the owner of this grade book can transfer to the budgetary basis.
     *
     * <p>
     * A requirement for transferring from a fee-paying to a budget-funded form of education is
     * the absence of "satisfactory" grades for the last two examination sessions (for exams,
     * "satisfactory" grades are acceptable in differentiated assessments).
     * </p>
     *
     * @return {@code true} if the transfer is possible, {@code false} otherwise
     */
    public boolean canBeBudget() {
        if (student.educationBasis() == EducationBasis.BUDGETARY) {
            return true;
        }
        if (semesters.isEmpty()) {
            throw new EmptyGradeBookException();
        }

        var lastSemesters = getLastSemesters(2);
        return lastSemesters.stream().allMatch(Semester::isPassed)
            && lastSemesters.stream()
            .flatMap(Semester::stream)
            .filter(subject -> subject.getType() == AssessmentType.EXAM)
            .map(Subject::getGrade)
            .allMatch(grade -> grade != DifferentiatedGrade.SATISFACTORY);
    }

    /**
     * Determines if the owner of this book can have an increased scholarship.
     *
     * <p>
     * Requirements for an increased scholarship: no satisfactory grades in the midterm assessment
     * </p>
     *
     * @return {@code true} if the student can have an increased scholarship, {@code false}
     *     otherwise
     */
    public boolean canHaveIncreasedScholarship() {
        if (semesters.isEmpty()) {
            throw new EmptyGradeBookException();
        }

        var lastSemester = getLastSemesters(1).get(0);
        return lastSemester.stream()
            .allMatch(subject -> subject.getGrade() != DifferentiatedGrade.SATISFACTORY);
    }

    /**
     * Determines if the owner of this grade book can have a diploma with honor.
     * Requirements for a diploma with honors:
     * <ul>
     * <li>75% of the grades in the diploma supplement (the final grade) are "excellent";</li>
     * <li>no final grades of "satisfactory" for both differentiated credits and exams;</li>
     * <li>a qualifying work with an "excellent" grade.</li>
     * </ul>
     *
     * @return {@code true} if the diploma with honor is possible, {@code false} otherwise
     */
    public boolean canBeHonored() {
        var currentSupplement = getCurrentSupplement();
        var hasSatisfactory = currentSupplement.values().stream()
            .anyMatch(grade -> grade == DifferentiatedGrade.SATISFACTORY);
        var excellentCount = currentSupplement.values().stream()
            .filter(grade -> grade == DifferentiatedGrade.EXCELLENT).count();
        var thesis = getThesis();
        var subjectsLeft = supplementSubjectsCount - currentSupplement.size();

        return thesis.map(subject ->
            subject.getGrade() == DifferentiatedGrade.EXCELLENT
                && (excellentCount + subjectsLeft) * 4 >= 3L * supplementSubjectsCount
                && !hasSatisfactory
        ).orElseGet(() ->
            excellentCount * 4 >= 3L * currentSupplement.size()
                && !hasSatisfactory
        );
    }

    private Map<String, Grade> getCurrentSupplement() {
        return semesters.stream()
            .flatMap(Semester::stream)
            .filter(subject -> subject.getType().isDifferentiated())
            .collect(Collectors.toMap(
                Subject::getName,
                Subject::getGrade,
                (oldValue, newValue) -> newValue
            ));
    }

    private Optional<Subject> getThesis() {
        return getLastSemesters(1).stream()
            .flatMap(Semester::stream)
            .filter(subject -> subject.getType() == AssessmentType.THESIS)
            .findAny();
    }

    private List<Semester> getLastSemesters(int n) {
        int lower = Math.max(0, semesters.size() - n);
        return semesters.subList(lower, semesters.size());
    }

    @Override
    public String toString() {
        final var newline = '\n';
        final var separator = newline + "=".repeat(60) + newline;

        var sb = new StringBuilder();
        sb.append("Зачетная книжка")
            .append(separator);

        sb.append("Студент: ")
            .append(student)
            .append(newline);
        sb.append("Основа обучения: ")
            .append(student.educationBasis().getName());

        for (var semester : semesters) {
            sb.append(separator).append(semester);
        }

        return sb.toString();
    }
}
