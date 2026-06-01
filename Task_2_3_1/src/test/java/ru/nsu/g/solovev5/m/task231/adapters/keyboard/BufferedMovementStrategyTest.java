package ru.nsu.g.solovev5.m.task231.adapters.keyboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

class BufferedMovementStrategyTest {
    @Test
    void put_shouldNot_exitBufferSize() {
        var strategy = new BufferedMovementStrategy(5);

        for (var i = 0; i < 5; i++) {
            assertTrue(strategy.put(MoveDirection.UP));
        }

        assertFalse(strategy.put(MoveDirection.UP));
    }

    @Test
    void put_shouldNot_acceptOppositeDirection() {
        var strategy = new BufferedMovementStrategy(5);
        assertTrue(strategy.put(MoveDirection.UP));
        assertFalse(strategy.put(MoveDirection.DOWN));
    }

    @Test
    void nextHead_should_promoteBuffer() {
        var strategy = new BufferedMovementStrategy(5);

        strategy.put(MoveDirection.UP);
        strategy.put(MoveDirection.LEFT);
        strategy.put(MoveDirection.DOWN);

        var point = new Point2D(0, 0);

        assertEquals(
            new Point2D(0, -1),
            strategy.nextHead(point)
        );
        assertEquals(
            new Point2D(-1, 0),
            strategy.nextHead(point)
        );
        assertEquals(
            new Point2D(0, 1),
            strategy.nextHead(point)
        );
    }

    @Test
    void nextHead_should_repeatMoveIfBufferIsEmpty() {
        var strategy = new BufferedMovementStrategy(5);

        strategy.put(MoveDirection.RIGHT);
        var point = new Point2D(0, 0);

        for (var i = 0; i < 5; i++) {
            assertEquals(
                new Point2D(1, 0),
                strategy.nextHead(point)
            );
        }
    }
}