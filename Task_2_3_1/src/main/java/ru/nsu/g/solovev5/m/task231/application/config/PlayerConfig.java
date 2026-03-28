package ru.nsu.g.solovev5.m.task231.application.config;

import ru.nsu.g.solovev5.m.task231.domain.strategies.MovementStrategy;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

/**
 * The player configuration.
 *
 * @param snakeHead the start coordinates of the player's snake's head
 * @param strategy  the strategy the player keep to
 */
public record PlayerConfig(Point2D snakeHead, MovementStrategy strategy) {
}
