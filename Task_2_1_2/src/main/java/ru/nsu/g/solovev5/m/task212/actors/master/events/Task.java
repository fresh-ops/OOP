package ru.nsu.g.solovev5.m.task212.actors.master.events;

import java.util.Arrays;

public class Task {
    private final int[][] chunks;
    private final boolean[] doneChunks;
    private int nextChunkIndex = 0;
    private boolean done;
    private boolean result = false;

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

    public boolean isDone() {
        return done;
    }

    public boolean getResult() {
        return result;
    }

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

    public synchronized void acceptChunkResult(Chunk chunk, boolean result) {
        if (chunk.chunkIndex() < 0 || chunk.chunkIndex() >= chunks.length) {
            throw new IndexOutOfBoundsException();
        }
        doneChunks[chunk.chunkIndex()] = true;
        this.result |= result;
    }

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