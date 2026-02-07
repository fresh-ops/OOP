package ru.nsu.g.solovev5.m.task211.primes;

import java.util.Arrays;

/**
 * A prime checker that uses a parallel streams to make calculations faster.
 */
public class ParallelStreamPrimeChecker implements PrimeChecker {
    @Override
    public boolean checkIfContainsNonPrime(int[] numbers) {
        if (numbers == null) {
            return false;
        }
        return Arrays.stream(numbers).parallel().anyMatch(n -> !checkIfPrime(n));
    }
}
