package ru.nsu.g.solovev5.m.task231.application;

import java.util.List;
import java.util.Set;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.services.CollisionService;

/**
 * Checks for collisions with enemies.
 */
public class CheckEnemiesCollisions {
    /**
     * Checks if there is a collision with other snake.
     *
     * @param player       the player to check
     * @param otherPlayers other players on the board
     * @return {@code true} if there is a collision with another snake, {@code false} otherwise
     */
    public boolean invoke(Player player, List<Player> otherPlayers) {
        var snake = player.snake();
        var head = snake.getHead();

        for (var otherPlayer : otherPlayers) {
            if (otherPlayer == player) {
                continue;
            }
            var segments = Set.copyOf(otherPlayer.snake().getSegments());
            if (CollisionService.collides(segments, head)) {
                return true;
            }
        }

        return false;
    }
}
