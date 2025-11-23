package ru.nsu.g.solovev5.m.gradebook.semester.subject.grades;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CreditGradeTest {
    @ParameterizedTest
    @MethodSource
    void checkName(CreditGrade grade) {
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
            Arguments.of(CreditGrade.PASS),
            Arguments.of(CreditGrade.FAIL)
        );
    }

    @Test
    void checkPositive() {
        assertTrue(
            CreditGrade.PASS.isPositive(),
            "The PASS grade should be positive"
        );
    }

    @Test
    void checkNegative() {
        assertFalse(
            CreditGrade.FAIL.isPositive(),
            "The FAIL grade should be negative"
        );
    }
}