package ru.nsu.g.solovev5.m.task231.application.dto;

import java.util.List;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

/**
 * A transferable copy of the snake.
 *
 * @param head     the snake's head
 * @param tail     the snake's tail
 * @param segments the segments of snake's body
 */
public record SnakeDto(
    Point2D head,
    Point2D tail,
    List<Point2D> segments
) {
}
