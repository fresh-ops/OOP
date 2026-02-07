package ru.nsu.g.solovev5.m.task211.primes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public abstract class PrimeCheckerImplementationTest {
    PrimeChecker checker;

    @BeforeEach
    void init() {
        checker = createPrimeChecker();
    }

    public abstract PrimeChecker createPrimeChecker();

    @Test
    void shouldReturnTrueWhenAllNumbersArePrime() {
        var numbers = new int[]{2, 3, 5, 7, 11, Integer.MAX_VALUE};

        assertTrue(
            checker.checkIfContainsPrime(numbers),
            "Checker should return true when all numbers are prime."
        );
    }

    @Test
    void shouldReturnFalseWhenAllNumbersAreNotPrime() {
        var numbers = new int[]{4, 6, 8, 9, 10, Integer.MIN_VALUE};

        assertFalse(
            checker.checkIfContainsPrime(numbers),
            "Checker should return false when all numbers are prime."
        );
    }

    @Test
    void shouldReturnFalseWhenArrayIsNull() {
        assertFalse(
            assertDoesNotThrow(
                () -> checker.checkIfContainsPrime(null),
                "Checker should not fail when the array is null."
            ),
            "Checker should return false when the array is null."
        );
    }

    @Test
    void shouldReturnFalseWhenArrayIsEmpty() {
        assertFalse(
            assertDoesNotThrow(
                () -> checker.checkIfContainsPrime(new int[0]),
                "Checker should not fail when the array is empty."
            ),
            "Checker should return false when the array is empty."
        );
    }

    @ParameterizedTest
    @MethodSource
    void shouldReturnTrueWhenThereIsAPrime(int[] numbers, String message) {
        assertTrue(
            checker.checkIfContainsPrime(numbers),
            "Checker should return true when there is a prime." + message
        );
    }

    static Stream<Arguments> shouldReturnTrueWhenThereIsAPrime() {
        return Stream.of(
            Arguments.of(new int[]{1, 1, 1, 1, 2}, "A prime is in the end."),
            Arguments.of(new int[]{37, 90, 35, 12, 68, 9}, "A prime is in the beginning."),
            Arguments.of(new int[]{121, 45, 73, 80, 63, 24}, "A prime is in the middle."),
            Arguments.of(new int[]{Integer.MAX_VALUE}, "A single number."),
            Arguments.of(
                new int[]{2_115_080_099, 2_115_172_081, 2_111_402_059, 2_107_544_023, 13,
                    2_110_759_249},
                "A bunch of big numbers."
            )
        );
    }
}
