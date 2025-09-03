package ru.nsu.g.solovev5.m.Task_1_1_1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void heapsort() {
        var array = new int[]{1, 2, 3, 10, 5, 6, 8, 11, 9, 16};
        Main.heapsort(array);
        assertArrayEquals(new int[]{1, 2, 3, 5, 6, 8, 9, 10, 11, 16}, array);

        var emptyArray = new int[0];
        Main.heapsort(emptyArray);
        assertArrayEquals(new int[0], emptyArray);

        var ascendOrderedArray = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Main.heapsort(ascendOrderedArray);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, ascendOrderedArray);

        var descendOrderedArray = new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        Main.heapsort(descendOrderedArray);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, descendOrderedArray);

        var duplicates = new int[]{1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 3, 1, 1, 1};
        Main.heapsort(duplicates);
        assertArrayEquals(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3}, duplicates);
    }

    @Test
    void main() {
        Main.main(new String[]{});
    }
}