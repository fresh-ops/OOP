package ru.nsu.g.solovev5.m.task212.actors.master.events;

import java.util.Arrays;
import java.util.function.BiConsumer;

/**
 * An outsourced chunk of tasks data.
 *
 * @param chunkIndex task local index
 * @param numbers    given data chunk
 * @param onResult   result callback
 */
public record Chunk(
    int chunkIndex,
    int[] numbers,
    BiConsumer<Chunk, Boolean> onResult
) {
    @Override
    public boolean equals(Object o) {
        if (o instanceof Chunk other) {
            return Arrays.equals(numbers, other.numbers) && chunkIndex == other.chunkIndex;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(numbers) ^ chunkIndex;
    }
}
