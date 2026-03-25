package ru.nsu.g.solovev5.m.task231.domain.snake;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import ru.nsu.g.solovev5.m.task231.domain.shared.Point2D;

/**
 * A model of a snake.
 */
public class Snake {
    private final Deque<Point2D> segments;

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
     * @param grow specifies whether to grow or not.
     * @throws NullPointerException if {@code head} is {@code null}
     */
    public void move(Point2D head, boolean grow) {
        putHead(head);

        if (!grow) {
            popTail();
        }
    }

    /**
     * Moves this snake to the given head.
     *
     * @param head the new head coordinates
     * @throws NullPointerException if {@code head} is {@code null}
     */
    public void move(Point2D head) {
        move(head, false);
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
