package ru.nsu.g.solovev5.m.gradebook.semester;

import java.util.Collections;
import java.util.Iterator;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ru.nsu.g.solovev5.m.gradebook.semester.exceptions.DuplicateSemesterSubjectsException;
import ru.nsu.g.solovev5.m.gradebook.semester.exceptions.IllegalSemesterNumberException;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.AssessmentType;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.Subject;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.grades.Grade;

/**
 * Represents an academic semester.
 */
public class Semester implements Iterable<Subject> {
    public static final int UPPER_NUMBER_BOUND = 8;
    public static final int LOWER_NUMBER_BOUND = 1;

    public static final String SEMESTER_HEADER = "Семестр";
    public static final String SUBJECT_HEADER = "Предмет";
    public static final String ASSESSMENT_TYPE_HEADER = "Вид контроля";
    public static final String GRADE_HEADER = "Оценка";

    private final Set<Subject> subjects;
    private final int number;
    private String formatString = null;

    private Semester(int number, Set<Subject> subjects) {
        this.subjects = Collections.unmodifiableSet(subjects);
        this.number = number;
    }

    /**
     * Creates a new semester with given number and subjects.
     *
     * @param number the number of a new semester. Must be between {@link #LOWER_NUMBER_BOUND} and
     *     {@link #UPPER_NUMBER_BOUND}
     * @param subjects the subjects inside the semester. Every subject should have unique name
     * @return a new semester
     * @throws IllegalSemesterNumberException if the passed number is out of bounds
     * @throws DuplicateSemesterSubjectsException if there are duplicates in subjects
     */
    public static Semester of(int number, Subject... subjects) {
        if (LOWER_NUMBER_BOUND > number || number > UPPER_NUMBER_BOUND) {
            throw new IllegalSemesterNumberException(number);
        }

        try {
            var setOfSubjects = Set.of(subjects);
            var setOfNames = setOfSubjects.stream()
                .map(Subject::getName)
                .collect(Collectors.toUnmodifiableSet());

            if (setOfSubjects.size() != setOfNames.size()) {
                throw new DuplicateSemesterSubjectsException();
            }

            return new Semester(number, setOfSubjects);
        } catch (IllegalArgumentException e) {
            throw new DuplicateSemesterSubjectsException(e);
        }
    }

    /**
     * Returns the number of this semester.
     *
     * @return the number of this semester
     */
    public int getNumber() {
        return number;
    }

    @Override
    public Iterator<Subject> iterator() {
        return subjects.iterator();
    }

    /**
     * Checks if all grades in this semester are positive.
     *
     * @return {@code true} if all grades are positive, {@code false} otherwise
     */
    public boolean isPassed() {
        return subjects.stream()
            .allMatch(subject -> subject.getGrade().isPositive());
    }

    /**
     * Calculates the average value of all differentiated subjects.
     *
     * @return the average of all differentiated grades
     */
    public OptionalDouble average() {
        return subjects.stream()
            .map(Subject::getGrade)
            .filter(Grade::isDifferentiated)
            .mapToDouble(grade -> (double) grade.getNumeric())
            .average();
    }

    public Stream<Subject> stream() {
        return subjects.stream();
    }

    @Override
    public String toString() {
        if (formatString == null) {
            createFormatString();
        }
        var sb = new StringBuilder();

        sb.append(SEMESTER_HEADER).append(' ').append(number).append('\n');
        sb.append(String.format(
            formatString, SUBJECT_HEADER, ASSESSMENT_TYPE_HEADER, GRADE_HEADER
        ));

        for (var subject : subjects) {
            sb.append(String.format(
                formatString,
                subject.getName(), subject.getType().getName(), subject.getGrade().getName()
            ));
        }

        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    private void createFormatString() {
        var maxNameLength = Math.max(
            subjects.stream()
                .map(Subject::getName)
                .mapToInt(String::length).max().orElse(0),
            7
        );
        var maxTypeLength = Math.max(
            subjects.stream()
                .map(Subject::getType)
                .map(AssessmentType::getName)
                .mapToInt(String::length).max().orElse(0),
            12
        );

        formatString = "%-" + maxNameLength + "s  %-" + maxTypeLength + "s  %s\n";
    }
}
