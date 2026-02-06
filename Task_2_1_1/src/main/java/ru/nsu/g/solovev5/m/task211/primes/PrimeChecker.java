package ru.nsu.g.solovev5.m.task211.primes;

/**
 * A common interface for searching a prime number in an array.
 */
public interface PrimeChecker {
    /**
     * Checks if passed numbers contain at least one prime number.
     *
     * @param numbers the numbers to check
     * @return {@code true} if the sequence of numbers contains a prime number, {@code false}
     *     otherwise
     */
    boolean checkIfContainsPrime(int[] numbers);

    /**
     * Checks if passed number is prime.
     *
     * @param number the number to check
     * @return {@code true} if the number is prime, {@code false} otherwise
     */
    default boolean checkIfPrime(int number) {
        if (number <= 1) {
            return false;
        }

        for (var divisor = 2; divisor <= number / divisor; divisor++) {
            if (number % divisor == 0) {
                return false;
            }
        }

        return true;
    }
}
