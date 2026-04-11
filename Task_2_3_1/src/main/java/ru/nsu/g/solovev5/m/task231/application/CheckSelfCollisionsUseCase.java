package ru.nsu.g.solovev5.m.task231.application;

import java.util.Set;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.services.CollisionService;

/**
 * Checks for collisions with own body.
 */
public class CheckSelfCollisionsUseCase {
    /**
     * Check if there is a self collision.
     *
     * @param player the player to check
     * @return {@code true} if there is a self collision, {@code false} otherwise
     */
    public boolean invoke(Player player) {
        var snake = player.snake();
        var head = snake.getHead();
        var body = snake.getBody();
        return CollisionService.collides(Set.copyOf(body), head);
    }
}
