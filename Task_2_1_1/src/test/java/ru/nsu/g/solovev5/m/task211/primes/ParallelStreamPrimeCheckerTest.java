package ru.nsu.g.solovev5.m.task211.primes;

/**
 * Test for {@link ParallelStreamPrimeChecker}.
 */
public class ParallelStreamPrimeCheckerTest extends PrimeCheckerImplementationTest{
    @Override
    public PrimeChecker createPrimeChecker() {
        return new ParallelStreamPrimeChecker();
    }
}
