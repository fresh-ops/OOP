package ru.nsu.g.solovev5.m.task212.actors.master.events;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import org.junit.jupiter.api.Test;

class TaskTest {
    @Test
    void isDone_should_returnFalse_when_noChunksAccepted() {
        var numbers = new int[]{1, 2, 3, 4, 5};
        var chunksNumber = 3;
        var task = new Task(numbers, chunksNumber);

        assertFalse(task.isDone());
    }

    @Test
    void isDone_should_returnTrue_when_allChunksAccepted() {
        var numbers = new int[]{1, 2, 3, 4, 5};
        var chunksNumber = 3;
        var task = new Task(numbers, chunksNumber);

        for (int i = 0; i < chunksNumber; i++) {
            var chunk = task.nextChunk();
            task.acceptChunkResult(chunk, false);
        }

        assertTrue(task.isDone());
    }

    @Test
    void getResult_should_returnFalse_when_noChunksAccepted() {
        var numbers = new int[]{1, 2, 3, 4, 5};
        var chunksNumber = 3;
        var task = new Task(numbers, chunksNumber);

        assertFalse(task.getResult());
    }

    @Test
    void getResult_should_returnFalse_when_NoAcceptedChunkResultsTrue() {
        var numbers = new int[]{1, 2, 3, 4, 5};
        var chunksNumber = 3;
        var task = new Task(numbers, chunksNumber);

        for (int i = 0; i < chunksNumber; i++) {
            var chunk = task.nextChunk();
            task.acceptChunkResult(chunk, false);
        }

        assertFalse(task.getResult());
    }

    @Test
    void getResult_should_returnTrue_when_atLeastOneAcceptedChunkResultsTrue() {
        var numbers = new int[]{1, 2, 3, 4, 5};
        var chunksNumber = 3;
        var task = new Task(numbers, chunksNumber);

        for (int i = 0; i < chunksNumber - 1; i++) {
            var chunk = task.nextChunk();
            task.acceptChunkResult(chunk, false);
        }
        var chunk = task.nextChunk();
        task.acceptChunkResult(chunk, true);

        assertTrue(task.getResult());
    }

    @Test
    void nextChunk_should_generateAtMostGivenNumberOfChunks() {
        var numbers = new int[]{1, 2, 3, 4, 5};
        var chunksNumber = 3;
        var task = new Task(numbers, chunksNumber);

        var seenChunks = new HashSet<Chunk>();
        var chunk = task.nextChunk();

        while (!seenChunks.contains(chunk)) {
            seenChunks.add(chunk);
            chunk = task.nextChunk();
        }

        assertTrue(seenChunks.size() <= chunksNumber);
    }

    @Test
    void nextChunk_shouldNot_addNewNumbers() {
        var numbers = new int[]{1, 2, 3, 4, 5};
        var chunksNumber = 3;
        var task = new Task(numbers, chunksNumber);

        for (int i = 0; i < chunksNumber; i++) {
            var chunk = task.nextChunk();
            for (var number : chunk.numbers()) {
                assertTrue(Arrays.stream(numbers).anyMatch(n -> n == number));
            }
        }
    }

    @Test
    void nextChunk_shouldNot_missNumbers() {
        var numbers = new int[]{1, 2, 3, 4, 5};
        var chunksNumber = 3;
        var task = new Task(numbers, chunksNumber);

        var seenNumbers = new ArrayList<Integer>();

        for (int i = 0; i < chunksNumber; i++) {
            var chunk = task.nextChunk();
            for (var number : chunk.numbers()) {
                seenNumbers.add(number);
            }
        }

        var boxedNumbers = Arrays.stream(numbers).boxed().toList();
        assertTrue(seenNumbers.containsAll(boxedNumbers));
    }
}