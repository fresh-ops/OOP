package ru.nsu.g.solovev5.m.task211.primes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class PrimeCheckerTest {
    PrimeChecker checker = numbers -> false;

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 127, Integer.MAX_VALUE})
    void shouldRecognizePrimeNumber(int prime) {
        assertTrue(
            checker.isPrime(prime),
            prime + " should be prime"
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, -1, 4, 9, 25, Integer.MIN_VALUE, Integer.MAX_VALUE - 1})
    void shouldRejectNonPrimeNumber(int nonPrime) {
        assertFalse(
            checker.isPrime(nonPrime),
            nonPrime + " should not be prime"
        );
    }

    @ParameterizedTest
    @CsvSource({
        "2_147_483_629, true", // самое большое простое для int
        "2_147_395_600, false" // наибольший полный квадрат, помещающийся в int
    })
    void shouldHandleBigNumber(int number, boolean expected) {
        assertEquals(
            expected, checker.isPrime(number),
            number + " handled incorrectly due to overflow"
        );
    }
}