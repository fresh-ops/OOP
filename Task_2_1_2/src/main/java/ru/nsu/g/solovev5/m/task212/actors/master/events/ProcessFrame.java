package ru.nsu.g.solovev5.m.task212.actors.master.events;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * A frame of numbers to process.
 *
 * @param id        process id
 * @param numbers   numbers to process
 * @param onSuccess success callback
 * @param onFail    fail callback
 */
public record ProcessFrame(
    UUID id,
    int[] numbers,
    Consumer<UUID> onSuccess,
    Consumer<UUID> onFail
) {
}
