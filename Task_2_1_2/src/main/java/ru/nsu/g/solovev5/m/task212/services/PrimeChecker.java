package ru.nsu.g.solovev5.m.task212.services;

import java.util.List;

/**
 * A service that checks numbers primality.
 */
public class PrimeChecker {
    /**
     * Checks if passed list contains non-prime number.
     *
     * @param numbers list of numbers to check
     * @return {@code true} if there is non-prime number, {@code false} otherwise
     */
    public boolean hasNonPrime(List<Integer> numbers) {
        return numbers.stream()
            .anyMatch(number -> !isPrime(number));
    }

    /**
     * Checks if passed number is prime.
     *
     * @param number number to check
     * @return {@code true} if the number is prime, {@code false} otherwise
     */
    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i < number / i; i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }
}
