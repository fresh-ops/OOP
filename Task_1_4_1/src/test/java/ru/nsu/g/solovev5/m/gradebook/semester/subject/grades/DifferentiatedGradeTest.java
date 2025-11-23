package ru.nsu.g.solovev5.m.gradebook.semester.subject.grades;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DifferentiatedGradeTest {
    @ParameterizedTest
    @MethodSource
    void checkName(DifferentiatedGrade grade) {
        assertAll(
            () -> assertNotEquals(
                "", grade.getName(),
                "The " + grade + " grade should have non-empty name"
            ),
            () -> assertDoesNotThrow(
                grade::getName,
                "The " + grade + " grade should provide its name without any exceptions"
            )
        );
    }

    static Stream<Arguments> checkName() {
        return Stream.of(
            Arguments.of(DifferentiatedGrade.EXCELLENT),
            Arguments.of(DifferentiatedGrade.GOOD),
            Arguments.of(DifferentiatedGrade.SATISFACTORY),
            Arguments.of(DifferentiatedGrade.FAIL)
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkPositive(DifferentiatedGrade grade) {
        assertTrue(
            grade.isPositive(),
            "The " + grade + " grade should be positive"
        );
    }

    static Stream<Arguments> checkPositive() {
        return Stream.of(
            Arguments.of(DifferentiatedGrade.EXCELLENT),
            Arguments.of(DifferentiatedGrade.GOOD),
            Arguments.of(DifferentiatedGrade.SATISFACTORY)
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkNegative(DifferentiatedGrade grade) {
        assertFalse(
            grade.isPositive(),
            "The " + grade + " grade should be negative"
        );
    }

    static Stream<Arguments> checkNegative() {
        return Stream.of(
            Arguments.of(DifferentiatedGrade.FAIL)
        );
    }
}