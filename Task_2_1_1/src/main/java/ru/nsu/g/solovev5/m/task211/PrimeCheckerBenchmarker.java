package ru.nsu.g.solovev5.m.task211;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ru.nsu.g.solovev5.m.task211.primes.MultiThreadPrimeChecker;
import ru.nsu.g.solovev5.m.task211.primes.ParallelStreamPrimeChecker;
import ru.nsu.g.solovev5.m.task211.primes.PrimeChecker;
import ru.nsu.g.solovev5.m.task211.primes.SequentialPrimeChecker;

/**
 * A program that compares execution speed of different implementation of the PrimeChecker
 * interface.
 */
public class PrimeCheckerBenchmarker {
    private static final int TEST_COUNT = 10;
    private static final int NUMBERS_IN_TEST = 100;

    /**
     * The entry point of a program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        var checkers = setUpPrimeCheckers();
        var testData = setUpTestData();

        var results = runTestWith(checkers, testData);

        for (var i = 0; i < results.size(); i++) {
            var formattedResult = String.format("%.2fns", results.get(i));
            System.out.println(checkers.get(i) + ": " + formattedResult);
        }
    }

    private static List<PrimeChecker> setUpPrimeCheckers() {
        var checkers = new ArrayList<PrimeChecker>();

        checkers.add(new SequentialPrimeChecker());
        for (var threadCount = 2; threadCount <= 8; threadCount++) {
            checkers.add(new MultiThreadPrimeChecker(threadCount));
        }
        checkers.add(new ParallelStreamPrimeChecker());

        return checkers;
    }

    private static int[] setUpTestData() {
        PrimeChecker checker = (numbers) -> false;
        var random = new Random();
        var testData = new ArrayList<Integer>();

        for (var i = 2_000_000_000; i < Integer.MAX_VALUE; i++) {
            if (checker.checkIfPrime(i) && random.nextBoolean()) {
                testData.add(i);
                if (testData.size() == NUMBERS_IN_TEST) {
                    break;
                }
            }
        }

        return testData.stream().mapToInt(Integer::intValue).toArray();
    }

    private static List<Double> runTestWith(List<PrimeChecker> checkers, int[] numbers) {
        var results = new ArrayList<Double>();

        for (var checker : checkers) {
            Runnable test = () -> checker.checkIfContainsNonPrime(numbers);
            results.add(averageTime(test, TEST_COUNT));
        }

        return results;
    }

    private static double averageTime(Runnable target, int numberOfTests) {
        double totalTime = 0;

        for (var i = 0; i < numberOfTests; i++) {
            totalTime += measureExecutionTime(target);
        }

        return totalTime / numberOfTests;
    }

    private static long measureExecutionTime(Runnable target) {
        var startTime = System.nanoTime();
        target.run();
        var endTime = System.nanoTime();

        return endTime - startTime;
    }
}
