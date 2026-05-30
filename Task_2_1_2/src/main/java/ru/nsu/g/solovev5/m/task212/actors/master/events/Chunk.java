package ru.nsu.g.solovev5.m.task212.actors.master.events;

import java.util.UUID;
import java.util.function.BiConsumer;

public record Chunk(
    int chunkIndex,
    int[] numbers,
    BiConsumer<Chunk, Boolean> onResult
) {
}
