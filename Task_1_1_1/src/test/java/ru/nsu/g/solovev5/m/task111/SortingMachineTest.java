package ru.nsu.g.solovev5.m.task111;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import java.util.Random;

class SortingMachineTest {

    @Test
    void checkDefaultArray() {
        var array = new int[]{1, 2, 3, 10, 5, 6, 8, 11, 9, 16};
        SortingMachine.heapsort(array);
        assertArrayEquals(new int[]{1, 2, 3, 5, 6, 8, 9, 10, 11, 16}, array);
    }

    @Test
    void checkSingleElement() {
        var singleElementArray = new int[]{1};
        SortingMachine.heapsort(singleElementArray);
        assertArrayEquals(new int[]{1}, singleElementArray);
    }

    @Test
    void checkEmptyArray() {
        var emptyArray = new int[0];
        SortingMachine.heapsort(emptyArray);
        assertArrayEquals(new int[0], emptyArray);
    }

    @Test
    void checkAscendOrder() {
        var ascendOrderedArray = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        SortingMachine.heapsort(ascendOrderedArray);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, ascendOrderedArray);
    }

    @Test
    void checkDescendOrder() {
        var descendOrderedArray = new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        SortingMachine.heapsort(descendOrderedArray);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, descendOrderedArray);
    }

    @Test
    void checkDuplicates() {
        var duplicates = new int[]{1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 3, 1, 1, 1};
        SortingMachine.heapsort(duplicates);
        assertArrayEquals(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3}, duplicates);
    }

    @Test
    void checkTimeComplexity() {
        System.out.println("Testing time");
        System.out.println("length\ttime start\ttime end\tduration");
        for (var i = 1; i <= 33_554_432 ; i *= 2) { //  проверим до длины 2 ** 25
            var array = generateRandomIntArray(i);
            final var timeStart = System.currentTimeMillis();

            SortingMachine.heapsort(array);

            final var timeEnd = System.currentTimeMillis();
            final var duration = timeEnd - timeStart;
            System.out.println(i + "\t" + timeStart + "\t" + timeEnd + "\t" + duration);
        }
    }

    private static int[] generateRandomIntArray(int length) {
        var array = new int[length];
        var random = new Random();

        for (var i = 0; i < array.length; i++) {
            array[i] = random.nextInt();
        }

        return array;
    }

    @Test
    void checkMain() {
        SortingMachine.main(new String[]{});
    }
}