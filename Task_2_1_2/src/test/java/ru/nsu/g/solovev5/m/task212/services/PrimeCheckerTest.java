package ru.nsu.g.solovev5.m.task212.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PrimeCheckerTest {
    @Test
    void hasNonPrime_should_returnFalse_when_allNumbersArePrime() {
        var numbers = new int[]{2, 3, 5, 7, 11};
        var checker = new PrimeChecker();

        assertFalse(checker.hasNonPrime(numbers));
    }

    @Test
    void hasNonPrime_should_returnTrue_when_thereIsNonPrimeNumber() {
        var numbers = new int[]{2, 3, 5, 7, 11, -1};
        var checker = new PrimeChecker();

        assertTrue(checker.hasNonPrime(numbers));
    }
}