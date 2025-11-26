package ru.nsu.g.solovev5.m.gradebook.semester;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.OptionalDouble;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.g.solovev5.m.gradebook.semester.exceptions.DuplicateSemesterSubjectsException;
import ru.nsu.g.solovev5.m.gradebook.semester.exceptions.IllegalSemesterNumberException;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.AssessmentType;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.Subject;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.grades.CreditGrade;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.grades.DifferentiatedGrade;

class SemesterTest {
    static Subject mathExam = Subject.of("Math", AssessmentType.EXAM, DifferentiatedGrade.GOOD);
    static Subject mathCredit = Subject.of("Math", AssessmentType.CREDIT, CreditGrade.PASS);
    static Subject english = Subject.of("English", AssessmentType.DIFFERENTIATED_CREDIT,
        DifferentiatedGrade.FAIL);
    static Subject pe = Subject.of("PE", AssessmentType.CREDIT, CreditGrade.PASS);
    static Subject history = Subject.of("History", AssessmentType.CREDIT, CreditGrade.FAIL);
    static Subject oop = Subject.of("OOP", AssessmentType.THESIS, DifferentiatedGrade.EXCELLENT);


    @ParameterizedTest
    @MethodSource
    void checkValidSemester(String message, int number, Subject... subjects) {
        var semester = assertDoesNotThrow(
            () -> Semester.of(number, subjects),
            message + ". Creating a semester with valid data should not cause any exception"
        );

        assertEquals(
            number, semester.getNumber(),
            message + ". The semester number should be the same as passed"
        );
    }

    static Stream<Arguments> checkValidSemester() {
        return Stream.of(
            Arguments.of("Ordinary semester", 3, new Subject[]{mathCredit, oop, pe, history}),
            Arguments.of("First semester", Semester.LOWER_NUMBER_BOUND, new Subject[]{english}),
            Arguments.of("Last semester", Semester.UPPER_NUMBER_BOUND,
                new Subject[]{pe, mathCredit}),
            Arguments.of("Empty semester", 6, new Subject[]{})
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkInvalidSemester(String message, Class<? extends Throwable> exception,
                              int number, Subject... subjects) {
        assertThrows(
            exception, () -> Semester.of(number, subjects),
            message + ". Creating a semester with invalid data should cause an exception"
        );
    }

    static Stream<Arguments> checkInvalidSemester() {
        return Stream.of(
            Arguments.of(
                "Negative number", IllegalSemesterNumberException.class,
                0, new Subject[]{mathCredit, oop}
            ),
            Arguments.of(
                "Too large number", IllegalSemesterNumberException.class,
                10, new Subject[]{english}
            ),
            Arguments.of(
                "Duplicate subjects", DuplicateSemesterSubjectsException.class,
                4, new Subject[]{history, history}
            ),
            Arguments.of(
                "Subjects with the same name", DuplicateSemesterSubjectsException.class,
                2, new Subject[]{mathExam, mathCredit}
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkPassed(String message, boolean isPassed, int number, Subject... subjects) {
        var semester = Semester.of(number, subjects);

        assertEquals(
            isPassed, semester.isPassed(),
            message
        );
    }

    static Stream<Arguments> checkPassed() {
        return Stream.of(
            Arguments.of("All passed", true, 1, new Subject[]{mathExam, pe, oop}),
            Arguments.of("Empty semester", true, 8, new Subject[]{}),
            Arguments.of("Failed credit", false, 5, new Subject[]{history, mathExam}),
            Arguments.of("Failed differentiated subject", false, 5, new Subject[]{english, oop}),
            Arguments.of("All failed", false, 1, new Subject[]{history, english})
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkAverage(String message, double average, int number, Subject... subjects) {
        var semester = Semester.of(number, subjects);
        var calculatedAverage = assertDoesNotThrow(
            semester::average,
            message + ". Calculating an average should not cause any exception"
        );

        assertTrue(
            calculatedAverage.isPresent(),
            message + ". If the semester has the right state, the value should be present"
        );
        assertEquals(
            average, calculatedAverage.getAsDouble(),
            message
        );
    }

    static Stream<Arguments> checkAverage() {
        return Stream.of(
            Arguments.of("One subject", 5.0, 3, new Subject[]{oop}),
            Arguments.of("Several subjects", 3.0, 6, new Subject[]{mathExam, english}),
            Arguments.of("Non-differentiated subjects", 3.5,
                5, new Subject[]{pe, oop, english, mathCredit})
        );
    }

    @Test
    void checkFailedAverage() {
        assertEquals(
            OptionalDouble.empty(),
            Semester.of(7, pe, history, mathCredit).average(),
            "If semester has no differentiated subjects, the result should be empty"
        );
    }

    @Test
    void checkToString() {
        var semester = Semester.of(3, oop);

        assertEquals(
            """
                Семестр 3
                Предмет  Вид контроля      Оценка
                OOP      Выпускная работа  Отлично""",
            semester.toString()
        );
    }
}