package ru.nsu.g.solovev5.m.task231.adapters.keyboard;

import java.util.ArrayDeque;
import java.util.Deque;
import ru.nsu.g.solovev5.m.task231.domain.strategies.MovementStrategy;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

/**
 * A movement strategy based on directions buffer.
 */
public class BufferedMovementStrategy implements MovementStrategy {
    private final Deque<MoveDirection> inputBuffer;
    private final int bufferSize;
    private MoveDirection lastDirection;

    /**
     * Creates a new BufferedMovementStrategy with the fixed buffer size.
     *
     * @param bufferSize the size of buffer
     */
    public BufferedMovementStrategy(int bufferSize) {
        inputBuffer = new ArrayDeque<>();
        this.bufferSize = bufferSize;
        lastDirection = MoveDirection.RIGHT;
    }

    @Override
    public Point2D nextHead(Point2D currentHead) {
        if (!inputBuffer.isEmpty()) {
            lastDirection = inputBuffer.removeFirst();
        }

        return move(currentHead, lastDirection);
    }

    /**
     * Puts a new direction in the buffer.
     *
     * @param direction the desired direction
     * @return {@code true} if
     */
    public boolean put(MoveDirection direction) {
        if (inputBuffer.size() >= bufferSize) {
            return false;
        }
        var lastInputted = inputBuffer.isEmpty() ? lastDirection : inputBuffer.getLast();
        if (lastInputted.opposite().equals(direction)) {
            return false;
        }
        inputBuffer.addLast(direction);
        return true;
    }

    /**
     * Creates a new point based on the given direction.
     *
     * @param point the base point
     * @param direction the move direction
     * @return a new point
     */
    private Point2D move(Point2D point, MoveDirection direction) {
        return switch (direction) {
            case UP -> new Point2D(point.x(),  point.y() - 1);
            case RIGHT -> new Point2D(point.x() + 1,  point.y());
            case DOWN -> new Point2D(point.x(),  point.y() + 1);
            case LEFT -> new Point2D(point.x() - 1,  point.y());
        };
    }
}
