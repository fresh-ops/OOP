package ru.nsu.g.solovev5.m.task212.actors.master.events;

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
}
