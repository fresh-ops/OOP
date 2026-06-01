package ru.nsu.g.solovev5.m.task231.domain.strategies;

import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

/**
 * Defines how to move a snake.
 */
public interface MovementStrategy {
    /**
     * Calculates a new head coordinates.
     *
     * @param currentHead the current snake's head
     * @return the coordinates of a new head
     */
    Point2D nextHead(Point2D currentHead);
}
