package ru.nsu.g.solovev5.m.gradebook.semester.subject;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.exceptions.BlankSubjectNameException;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.exceptions.IllegalGradeException;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.exceptions.IllegalSubjectNameException;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.grades.CreditGrade;
import ru.nsu.g.solovev5.m.gradebook.semester.subject.grades.DifferentiatedGrade;

class SubjectTest {
    @ParameterizedTest
    @MethodSource
    void checkValidDifferentiatedSubject(String message, String name, AssessmentType type,
                                         DifferentiatedGrade grade) {
        var subject = assertDoesNotThrow(
            () -> Subject.of(name, type, grade),
            message + ". Creating a subject with valid data should not cause any exception"
        );

        assertAll(
            () -> assertEquals(
                name, subject.name,
                message + ". The name field should be exactly the same as passed to a factory"
            ),
            () -> assertEquals(
                type, subject.type,
                message + ". The type field should be exactly the same as passed to a factory"
            ),
            () -> assertEquals(
                grade, subject.grade,
                message + ". The grade field should be exactly the same as passed to a factory"
            )
        );
    }

    static Stream<Arguments> checkValidDifferentiatedSubject() {
        var names = List.of("Math", "PE", "History", "Object Oriented Programming", "English 2B");
        var types = List.of(AssessmentType.values());
        var grades = List.of(DifferentiatedGrade.values());

        return names.stream()
            .flatMap(name -> types.stream()
                .filter(AssessmentType::isDifferentiated)
                .flatMap(type -> grades.stream().map(
                        grade -> Arguments.of(
                            name + "/" + type + "/" + grade,
                            name, type, grade
                        )
                    )
                )
            );
    }

    @ParameterizedTest
    @MethodSource
    void checkValidCreditSubject(String message, String name, AssessmentType type,
                                 CreditGrade grade) {
        var subject = assertDoesNotThrow(
            () -> Subject.of(name, type, grade),
            message + ". Creating a subject with valid data should not cause any exception"
        );

        assertAll(
            () -> assertEquals(
                name, subject.name,
                message + ". The name field should be exactly the same as passed to a factory"
            ),
            () -> assertEquals(
                type, subject.type,
                message + ". The type field should be exactly the same as passed to a factory"
            ),
            () -> assertEquals(
                grade, subject.grade,
                message + ". The grade field should be exactly the same as passed to a factory"
            )
        );
    }

    static Stream<Arguments> checkValidCreditSubject() {
        var names = List.of("Math", "PE", "History", "Object Oriented Programming", "English 2B");
        var types = List.of(AssessmentType.values());
        var grades = List.of(CreditGrade.values());

        return names.stream()
            .flatMap(name -> types.stream()
                .filter(type -> !type.isDifferentiated())
                .flatMap(type -> grades.stream().map(
                        grade -> Arguments.of(
                            name + "/" + type + "/" + grade,
                            name, type, grade
                        )
                    )
                )
            );
    }

    @ParameterizedTest
    @MethodSource
    void checkInvalidDifferentiatedSubject(String message, String name, AssessmentType type,
                                           DifferentiatedGrade grade) {
        assertThrows(
            IllegalGradeException.class,
            () -> Subject.of(name, type, grade),
            message + ". A mismatched grade should cause an exception"
        );
    }

    static Stream<Arguments> checkInvalidDifferentiatedSubject() {
        var names = List.of("Math", "PE", "History", "Object Oriented Programming", "English 2B");
        var types = List.of(AssessmentType.values());
        var grades = List.of(DifferentiatedGrade.values());

        return names.stream()
            .flatMap(name -> types.stream()
                .filter(type -> !type.isDifferentiated())
                .flatMap(type -> grades.stream().map(
                        grade -> Arguments.of(
                            name + "/" + type + "/" + grade,
                            name, type, grade
                        )
                    )
                )
            );
    }

    @ParameterizedTest
    @MethodSource
    void checkInvalidCreditSubject(String message, String name, AssessmentType type,
                                   CreditGrade grade) {
        assertThrows(
            IllegalGradeException.class,
            () -> Subject.of(name, type, grade),
            message + ". A mismatched grade should cause an exception"
        );
    }

    static Stream<Arguments> checkInvalidCreditSubject() {
        var names = List.of("Math", "PE", "History", "Object Oriented Programming", "English 2B");
        var types = List.of(AssessmentType.values());
        var grades = List.of(CreditGrade.values());

        return names.stream()
            .flatMap(name -> types.stream()
                .filter(AssessmentType::isDifferentiated)
                .flatMap(type -> grades.stream().map(
                        grade -> Arguments.of(
                            name + "/" + type + "/" + grade,
                            name, type, grade
                        )
                    )
                )
            );
    }

    @ParameterizedTest
    @MethodSource
    void checkDifferentiatedSubjectEquals(String message, String name, AssessmentType type,
                                          DifferentiatedGrade grade) {
        var subject1 = Subject.of(name, type, grade);
        var subject2 = Subject.of(name, type, grade);

        assertAll(
            () -> assertEquals(
                subject1, subject2,
                message + ". Two subjects with equal fields should be equal"
            ),
            () -> assertEquals(
                subject1.hashCode(), subject2.hashCode(),
                message + ". Two equal subjects should have equal hash codes"
            )
        );
    }

    static Stream<Arguments> checkDifferentiatedSubjectEquals() {
        var names = List.of("Math", "PE", "History", "Object Oriented Programming", "English 2B");
        var types = List.of(AssessmentType.values());
        var grades = List.of(DifferentiatedGrade.values());

        return names.stream()
            .flatMap(name -> types.stream()
                .filter(AssessmentType::isDifferentiated)
                .flatMap(type -> grades.stream().map(
                        grade -> Arguments.of(
                            name + "/" + type + "/" + grade,
                            name, type, grade
                        )
                    )
                )
            );
    }

    @ParameterizedTest
    @MethodSource
    void checkCreditSubjectEquals(String message, String name, AssessmentType type,
                                  CreditGrade grade) {
        var subject1 = Subject.of(name, type, grade);
        var subject2 = Subject.of(name, type, grade);

        assertAll(
            () -> assertEquals(
                subject1, subject2,
                message + ". Two subjects with equal fields should be equal"
            ),
            () -> assertEquals(
                subject1.hashCode(), subject2.hashCode(),
                message + ". Two equal subjects should have equal hash codes"
            )
        );
    }

    static Stream<Arguments> checkCreditSubjectEquals() {
        var names = List.of("Math", "PE", "History", "Object Oriented Programming", "English 2B");
        var types = List.of(AssessmentType.values());
        var grades = List.of(CreditGrade.values());

        return names.stream()
            .flatMap(name -> types.stream()
                .filter(type -> !type.isDifferentiated())
                .flatMap(type -> grades.stream().map(
                        grade -> Arguments.of(
                            name + "/" + type + "/" + grade,
                            name, type, grade
                        )
                    )
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
    void checkInvalidDifferentiatedSubjectName(String message,
                                               Class<? extends IllegalSubjectNameException>
                                                   exception,
                                               String name, AssessmentType type,
                                               DifferentiatedGrade grade) {
        assertThrows(
            exception,
            () -> Subject.of(name, type, grade),
            message + ". An invalid name should cause an exception"
        );
    }

    static Stream<Arguments> checkInvalidDifferentiatedSubjectName() {
        var messages = List.of("empty string", "spaces", "tabs", "line feed", "null");
        var names = Arrays.asList("", "  ", "\t\t", "\n", null);
        var types = List.of(AssessmentType.values());
        var grades = List.of(DifferentiatedGrade.values());

        return IntStream.range(0, messages.size())
            .boxed()
            .flatMap(i -> types.stream()
                .filter(AssessmentType::isDifferentiated)
                .flatMap(type -> grades.stream()
                    .map(grade -> Arguments.of(
                        messages.get(i),
                        names.get(i) == null ? IllegalSubjectNameException.class
                            : BlankSubjectNameException.class,
                        names.get(i), type, grade
                    ))
                )
            );
    }

    @ParameterizedTest
    @MethodSource
    void checkInvalidCreditSubjectName(String message,
                                       Class<? extends IllegalSubjectNameException>
                                           exception,
                                       String name, AssessmentType type,
                                       CreditGrade grade) {
        assertThrows(
            exception,
            () -> Subject.of(name, type, grade),
            message + ". An invalid name should cause an exception"
        );
    }

    static Stream<Arguments> checkInvalidCreditSubjectName() {
        var messages = List.of("empty string", "spaces", "tabs", "line feed", "null");
        var names = Arrays.asList("", "  ", "\t\t", "\n", null);
        var types = List.of(AssessmentType.values());
        var grades = List.of(CreditGrade.values());

        return IntStream.range(0, messages.size())
            .boxed()
            .flatMap(i -> types.stream()
                .filter(type -> !type.isDifferentiated())
                .flatMap(type -> grades.stream()
                    .map(grade -> Arguments.of(
                        messages.get(i),
                        names.get(i) == null ? IllegalSubjectNameException.class
                            : BlankSubjectNameException.class,
                        names.get(i), type, grade
                    ))
                )
            );
    }
}
