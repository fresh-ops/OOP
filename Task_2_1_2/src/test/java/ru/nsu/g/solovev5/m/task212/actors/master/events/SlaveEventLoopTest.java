package ru.nsu.g.solovev5.m.task212.actors.master.events;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class SlaveEventLoopTest {
    @Test
    void isRunning_should_returnFalse_when_loopIsNotRunning() {
        var loop = new SlaveEventLoop(null, UUID.randomUUID());

        assertFalse(loop.isRunning());
    }

    @Test
    void isBusy_should_returnFalse_when_loopIsNotRunning() {
        var loop = new SlaveEventLoop(null, UUID.randomUUID());

        assertFalse(loop.isBusy());
    }
}