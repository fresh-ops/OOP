package ru.nsu.g.solovev5.m.gradebook.semester.subject;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.exceptions.BlankSubjectNameException;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.exceptions.IllegalGradeException;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.exceptions.IllegalSubjectNameException;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.grades.CreditGrade;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.grades.DifferentiatedGrade;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.grades.Grade;

class SubjectTest {
    @ParameterizedTest
    @MethodSource
    void checkValidSubject(String message, String name, AssessmentType type, Grade grade) {
        var subject = assertDoesNotThrow(
            () -> Subject.of(name, type, grade),
            message + ". Creating a subject with valid data should not cause any exception"
        );

        assertAll(
            () -> assertEquals(
                name, subject.getName(),
                message + ". The name field should be exactly the same as passed to a factory"
            ),
            () -> assertEquals(
                type, subject.getType(),
                message + ". The type field should be exactly the same as passed to a factory"
            ),
            () -> assertEquals(
                grade, subject.getGrade(),
                message + ". The grade field should be exactly the same as passed to a factory"
            )
        );
    }

    static Stream<Arguments> checkValidSubject() {
        return Stream.of(
            Arguments.of(
                "Differentiated Subject", "Math",
                AssessmentType.EXAM, DifferentiatedGrade.FAIL
            ),
            Arguments.of(
                "Credit Subject", "English",
                AssessmentType.CREDIT, CreditGrade.PASS
            ),
            Arguments.of(
                "Cyrillic Letters", "Основы Российской Государственности",
                AssessmentType.DIFFERENTIATED_CREDIT, DifferentiatedGrade.GOOD
            ),
            Arguments.of(
                "Chinese Hieroglyphs", "面向对象编程",
                AssessmentType.THESIS, DifferentiatedGrade.EXCELLENT
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkInvalidGrade(String message, String name, AssessmentType type, Grade grade) {
        assertThrows(
            IllegalGradeException.class,
            () -> Subject.of(name, type, grade),
            message + ". A mismatched grade should cause an exception"
        );
    }

    static Stream<Arguments> checkInvalidGrade() {
        return Stream.of(
            Arguments.of(
                "Differentiated Assessment with Credit Grade", "Graphs",
                AssessmentType.EXAM, CreditGrade.PASS
            ),
            Arguments.of(
                "Credit Assessment with Differentiated Grade", "PE",
                AssessmentType.CREDIT, DifferentiatedGrade.FAIL
            )
        );
    }

    @Test
    void checkSubjectEquals() {
        var subject1 = Subject.of("Math", AssessmentType.EXAM, DifferentiatedGrade.SATISFACTORY);
        var subject2 = Subject.of("Math", AssessmentType.EXAM, DifferentiatedGrade.SATISFACTORY);

        assertAll(
            () -> assertEquals(
                subject1, subject2,
                "Two subjects with equal fields should be equal"
            ),
            () -> assertEquals(
                subject1.hashCode(), subject2.hashCode(),
                "Two equal subjects should have equal hash codes"
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkSubjectsNotEqual(String message, Subject subject, Object object) {
        assertNotEquals(
            subject, object,
            message
        );
    }

    static Stream<Arguments> checkSubjectsNotEqual() {
        return Stream.of(
            Arguments.of(
                "Different classes",
                Subject.of("Math", AssessmentType.EXAM, DifferentiatedGrade.GOOD), "Math"
            ),
            Arguments.of(
                "Different names",
                Subject.of("OOP", AssessmentType.CREDIT, CreditGrade.PASS),
                Subject.of("English", AssessmentType.CREDIT, CreditGrade.PASS)
            ),
            Arguments.of(
                "Different assessment types",
                Subject.of("PE", AssessmentType.DIFFERENTIATED_CREDIT, DifferentiatedGrade.GOOD),
                Subject.of("PE", AssessmentType.EXAM, DifferentiatedGrade.GOOD)
            ),
            Arguments.of(
                "Different grades",
                Subject.of("PE", AssessmentType.DIFFERENTIATED_CREDIT, DifferentiatedGrade.FAIL),
                Subject.of("PE", AssessmentType.DIFFERENTIATED_CREDIT, DifferentiatedGrade.GOOD)
            ),
            Arguments.of(
                "No matches",
                Subject.of("OOP", AssessmentType.EXAM, DifferentiatedGrade.SATISFACTORY),
                Subject.of("OS", AssessmentType.CREDIT, CreditGrade.FAIL)
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkInvalidSubjectName(String message,
                                 Class<? extends IllegalSubjectNameException> exception,
                                 String name, AssessmentType type, Grade grade) {
        assertThrows(
            exception,
            () -> Subject.of(name, type, grade),
            message + ". An invalid name should cause an exception"
        );
    }

    static Stream<Arguments> checkInvalidSubjectName() {
        return Stream.of(
            Arguments.of(
                "Empty name", BlankSubjectNameException.class,
                "", AssessmentType.CREDIT, DifferentiatedGrade.FAIL
            ),
            Arguments.of(
                "Spaces", BlankSubjectNameException.class,
                "      ", AssessmentType.EXAM, DifferentiatedGrade.EXCELLENT
            ),
            Arguments.of(
                "Tabulations", BlankSubjectNameException.class,
                "\t\t\t\t", AssessmentType.THESIS, DifferentiatedGrade.SATISFACTORY
            ),
            Arguments.of(
                "Null name", IllegalSubjectNameException.class,
                null, AssessmentType.CREDIT, CreditGrade.PASS
            )
        );
    }
}
