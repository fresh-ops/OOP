package ru.nsu.g.solovev5.m.task211.primes;

public class ParallelStreamPrimeCheckerTest extends PrimeCheckerImplementationTest{
    @Override
    public PrimeChecker createPrimeChecker() {
        return new ParallelStreamPrimeChecker();
    }
}
