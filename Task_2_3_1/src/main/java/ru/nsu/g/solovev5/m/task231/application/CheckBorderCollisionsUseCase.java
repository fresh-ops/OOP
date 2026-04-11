package ru.nsu.g.solovev5.m.task231.application;

import ru.nsu.g.solovev5.m.task231.domain.entities.Player;

/**
 * Checks for border collisions.
 */
public class CheckBorderCollisionsUseCase {
    /**
     * Checks if there is a border collision.
     *
     * @param player  the player to check
     * @param rows    the number of rows on the game board
     * @param columns the number of columns on the game board
     * @return {@code true} if there is a border collision, {@code false} otherwise
     */
    public boolean invoke(Player player, int rows, int columns) {
        var head = player.snake().getHead();

        return head.x() < 0 || head.y() < 0 || head.x() > columns || head.y() > rows;
    }
}
