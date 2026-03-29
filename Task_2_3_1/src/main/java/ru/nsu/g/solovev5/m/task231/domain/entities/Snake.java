package ru.nsu.g.solovev5.m.task231.domain.entities;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import ru.nsu.g.solovev5.m.task231.domain.exceptions.SnakeZeroLengthException;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

/**
 * A model of a snake.
 */
public class Snake {
    private final Deque<Point2D> segments;
    private int growthTicks = 0;

    /**
     * Create a new snake with given head.
     *
     * @param head the coordinates of the snake's head
     * @throws NullPointerException if {@code head} is {@code null}
     */
    public Snake(Point2D head) {
        Objects.requireNonNull(head, "head must not be null");

        segments = new ArrayDeque<>();
        segments.add(head);
    }

    /**
     * Create a new snake with head at (0, 0).
     */
    public Snake() {
        this(new Point2D(0, 0));
    }

    /**
     * Moves this snake to the given head.
     *
     * @param head the new head coordinates
     * @throws NullPointerException if {@code head} is {@code null}
     */
    public void move(Point2D head) {
        putHead(head);

        if (growthTicks <= 0) {
            popTail();
        } else {
            growthTicks--;
        }
    }

    public void addGrowthTicks(int growthTicks) {
        this.growthTicks += growthTicks;
    }

    /**
     * Returns a list of this snake's segments coordinates.
     *
     * @return a list of coordinates
     */
    public List<Point2D> getSegments() {
        return List.copyOf(segments);
    }

    /**
     * Returns all the snake's segments except the head.
     *
     * @return a snake's body segments
     */
    public List<Point2D> getBody() {
        var segments = getSegments();
        return segments.subList(1, segments.size());
    }

    /**
     * Returns the length of this snake. Actually the number of segments.
     *
     * @return the length of this snake
     */
    public int getLength() {
        return segments.size();
    }

    /**
     * Returns a head of this snake.
     *
     * @return a head of this snake.
     */
    public Point2D getHead() {
        return segments.getFirst();
    }

    /**
     * Puts a new head to this snake.
     *
     * @param head the new head
     * @throws NullPointerException if {@code head} is {@code null}
     */
    public void putHead(Point2D head) {
        Objects.requireNonNull(head, "head must not be null");
        segments.addFirst(head);
    }

    /**
     * Removes the tail of this snake.
     *
     * @return the removed tail
     * @throws SnakeZeroLengthException when trying to shrink this snake to zero length
     */
    public Point2D popTail() {
        if (segments.size() == 1) {
            throw new SnakeZeroLengthException();
        }
        return segments.removeLast();
    }
}
