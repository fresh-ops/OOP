package ru.nsu.g.solovev5.m.task211.primes;

/**
 * A prime checker that checks numbers one by one.
 */
public class SequentialPrimeChecker implements PrimeChecker {
    @Override
    public boolean containsNonPrime(int[] numbers) {
        if (numbers == null) {
            return false;
        }

        for (var number : numbers) {
            if (!isPrime(number)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "SequentialPrimeChecker{}";
    }
}
