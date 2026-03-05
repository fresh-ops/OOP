package ru.nsu.g.solovev5.m.task211.primes;

class SequentialPrimeCheckerTest extends PrimeCheckerImplementationTest {

    @Override
    public PrimeChecker createPrimeChecker() {
        return new SequentialPrimeChecker();
    }
}