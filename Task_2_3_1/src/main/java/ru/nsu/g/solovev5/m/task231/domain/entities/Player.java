package ru.nsu.g.solovev5.m.task231.domain.entities;

import ru.nsu.g.solovev5.m.task231.domain.strategies.MovementStrategy;

/**
 * Represents a player.
 *
 * @param snake    the player's snake
 * @param strategy the strategy player is keep to
 */
public record Player(Snake snake, MovementStrategy strategy) {
}
