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
    void shouldReturnFalseWhenAllNumbersArePrime() {
        var numbers = new int[]{2, 3, 5, 7, 11, Integer.MAX_VALUE};

        assertFalse(
            checker.checkIfContainsNonPrime(numbers),
            "Checker should return false when all numbers are prime."
        );
    }

    @Test
    void shouldReturnTrueWhenAllNumbersAreNonPrime() {
        var numbers = new int[]{4, 6, 8, 9, 0, Integer.MIN_VALUE};

        assertTrue(
            checker.checkIfContainsNonPrime(numbers),
            "Checker should return true when all numbers are non-prime."
        );
    }

    @Test
    void shouldReturnFalseWhenArrayIsNull() {
        assertFalse(
            assertDoesNotThrow(
                () -> checker.checkIfContainsNonPrime(null),
                "Checker should not fail when the array is null."
            ),
            "Checker should return false when the array is null."
        );
    }

    @Test
    void shouldReturnFalseWhenArrayIsEmpty() {
        assertFalse(
            assertDoesNotThrow(
                () -> checker.checkIfContainsNonPrime(new int[0]),
                "Checker should not fail when the array is empty."
            ),
            "Checker should return false when the array is empty."
        );
    }

    @ParameterizedTest
    @MethodSource
    void shouldReturnTrueWhenThereIsANonPrime(int[] numbers, String message) {
        assertTrue(
            checker.checkIfContainsNonPrime(numbers),
            "Checker should return true when there is a non-prime." + message
        );
    }

    static Stream<Arguments> shouldReturnTrueWhenThereIsANonPrime() {
        return Stream.of(
            Arguments.of(new int[]{2, 3, 5, 7, 0}, "A non-prime is in the end."),
            Arguments.of(new int[]{-101, 17, 19, 73, 11}, "A non-prime is in the beginning."),
            Arguments.of(new int[]{89, 37, 41, 55, 5, 29, 31}, "A non-prime is in the middle."),
            Arguments.of(new int[]{1}, "A single number."),
            Arguments.of(
                new int[]{
                    2_116_046_237, 2_116_046_281, 2_116_048_079,
                    2_116_048_087, 2_116_043_381, 2_115_407_200,
                    2_116_044_629, 2_116_044_631, 2_116_043_593
                },
                "A bunch of big prime numbers and a non-prime one."
            )
        );
    }
}
