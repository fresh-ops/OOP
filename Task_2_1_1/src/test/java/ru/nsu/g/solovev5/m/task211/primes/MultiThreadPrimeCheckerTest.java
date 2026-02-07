package ru.nsu.g.solovev5.m.task211.primes;

/**
 * Test for {@link MultiThreadPrimeChecker}.
 */
public class MultiThreadPrimeCheckerTest extends PrimeCheckerImplementationTest {
    @Override
    public PrimeChecker createPrimeChecker() {
        return new MultiThreadPrimeChecker(8);
    }
}
