package ru.nsu.g.solovev5.m.task231.application.transport;

import java.util.List;
import ru.nsu.g.solovev5.m.task231.domain.shared.Point2D;

/**
 * A transferable copy of the snake.
 *
 * @param segments the segments of snake's body
 */
public record SnakeRecord(List<Point2D> segments) {
}
