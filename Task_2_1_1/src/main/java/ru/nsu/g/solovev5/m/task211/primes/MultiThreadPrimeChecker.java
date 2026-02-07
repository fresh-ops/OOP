package ru.nsu.g.solovev5.m.task211.primes;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A prime checker that uses several threads to make calculations parallel.
 */
public class MultiThreadPrimeChecker implements PrimeChecker {
    private final int threadCount;

    /**
     * Creates a new MultiThreadPrimeChecker with given number of threads.
     *
     * @param threadCount the number of threads a new prime checker should use during the work
     */
    public MultiThreadPrimeChecker(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public boolean checkIfContainsPrime(int[] numbers) {
        if (numbers ==  null || numbers.length == 0) {
            return false;
        }

        var chunkSize = (numbers.length + threadCount - 1) / threadCount;
        AtomicBoolean result = new AtomicBoolean(false);
        var threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            var start = i * chunkSize;
            var end = Math.min((i + 1) * chunkSize,  numbers.length);
            threads[i] = new PrimeCheckerThread(numbers, start, end, result);
            threads[i].start();
        }

        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result.get();
    }

    /**
     * A thread that searches a prime number in a certain interval in array.
     */
    private class PrimeCheckerThread extends Thread {
        private final int[] numbers;
        private final int start;
        private final int end;
        private final AtomicBoolean primeFound;

        /**
         * Creates a new PrimeCheckerThread.
         *
         * @param numbers an array of numbers where to search for a prime number
         * @param start a position to start search from
         * @param end a position to stop search at
         * @param primeFound a flag if a prime number found somewhere
         */
        PrimeCheckerThread(int[] numbers, int start, int end,  AtomicBoolean primeFound) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
            this.primeFound = primeFound;
        }

        @Override
        public void run() {
            if (start >= end || primeFound.get()) {
                return;
            }

            for (int i = start; i < end && !primeFound.get(); i++) {
                if (primeFound.get() || Thread.currentThread().isInterrupted()) {
                    return;
                }

                if (checkIfPrime(numbers[i])) {
                    primeFound.set(true);
                    return;
                }
            }
        }
    }
}
