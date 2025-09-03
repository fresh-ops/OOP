package ru.nsu.g.solovev5.m.task111;

import java.util.Arrays;

/**
 * <p>Main class of the application.</p>
 */
public class Main {
    /**
     * <p>Program entry point.</p>
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        var array = new int[]{1, 2, 3, 10, 5, 6, 8, 11, 9, 16};
        heapsort(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * <p>Heapsort method. Sorts an array in place</p>
     *
     * @param array The array to be sorted
     */
    public static void heapsort(int[] array) {
        for (var i = 0; i < array.length; i++) {
            siftUp(array, i);
        }

        for (var i = array.length; i > 0; i--) {
            extractHead(array, i);
        }
    }

    private static void extractHead(int[] array, final int heapLength) {
        swap(array, 0, heapLength - 1);

        siftDown(array, 0, heapLength - 1);
    }

    private static void siftUp(int[] array, final int position) {
        if (position <= 0) {
            return;
        }

        final var parentPosition = (position - 1) / 2;

        if (compare(array[position], array[parentPosition])) {
            swap(array, position, parentPosition);

            siftUp(array, parentPosition);
        }
    }

    private static void siftDown(int[] array, final int position, final int heapLength) {
        final var leftChild = position * 2 + 1;
        final var rightChild = position * 2 + 2;

        if (leftChild >= heapLength) {
            return;
        }

        var childToSwap = rightChild;
        if (rightChild >= heapLength || compare(array[leftChild], array[rightChild])) {
            childToSwap = leftChild;
        }

        if (compare(array[childToSwap], array[position])) {
            swap(array, position, childToSwap);

            siftDown(array, childToSwap, heapLength);
        }
    }

    private static boolean compare(final int newRoot, final int root) {
        return newRoot > root;
    }

    private static void swap(int[] array, final int i, final int j) {
        final var temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}