package ru.nsu.g.solovev5.m.task212.actors.master.events;

import java.util.Arrays;

/**
 * An object representing processing task.
 */
public class Task {
    private final int[][] chunks;
    private final boolean[] doneChunks;
    private int nextChunkIndex = 0;
    private boolean done;
    private boolean result = false;

    /**
     * Creates a new Task.
     *
     * @param numbers      numbers to process
     * @param chunksNumber number of chunks to split data in
     */
    public Task(int[] numbers, int chunksNumber) {
        var count = Math.min(numbers.length, chunksNumber);
        var size = numbers.length / count;
        size += numbers.length % count == 0 ? 0 : 1;

        chunks = new int[count][];
        for (var i = 0; i * size < numbers.length; i++) {
            var start = i * size;
            var end = Math.min(start + size, numbers.length);
            chunks[i] = Arrays.copyOfRange(numbers, start, end);
        }

        doneChunks = new boolean[chunksNumber];
    }

    /**
     * Checks if this task is done.
     *
     * @return {@code true} if this task is done, {@code false} otherwise
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Returns the result of this task.
     *
     * @return {@code true} if found a non-prime number in given numbers, {@code false} otherwise
     */
    public boolean getResult() {
        return result;
    }

    /**
     * Generates a new chunk to process.
     *
     * @return a new chunk of numbers
     */
    public Chunk nextChunk() {
        updateNextChunkIndexAndDoneFlag();
        var chunk = new Chunk(
            nextChunkIndex,
            chunks[nextChunkIndex++],
            this::acceptChunkResult
        );
        nextChunkIndex %= chunks.length;
        return chunk;
    }

    /**
     * Accepts the result of chunk processing.
     *
     * @param chunk  the processed chunk
     * @param result the result of processing
     */
    public synchronized void acceptChunkResult(Chunk chunk, boolean result) {
        if (chunk.chunkIndex() < 0 || chunk.chunkIndex() >= chunks.length) {
            throw new IndexOutOfBoundsException();
        }
        doneChunks[chunk.chunkIndex()] = true;
        this.result |= result;
    }

    /**
     * Updates the index of next chunk and done flag.
     */
    private synchronized void updateNextChunkIndexAndDoneFlag() {
        if (done) {
            return;
        }

        for (int i = nextChunkIndex; i < chunks.length; i++) {
            if (!doneChunks[i]) {
                nextChunkIndex = i;
                return;
            }
        }
        for (int i = 0; i < nextChunkIndex; i++) {
            if (!doneChunks[i]) {
                nextChunkIndex = i;
                return;
            }
        }

        done = true;
    }
}