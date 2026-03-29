package ru.nsu.g.solovev5.m.task231.application;

import java.util.List;
import java.util.Set;
import ru.nsu.g.solovev5.m.task231.domain.entities.Food;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.services.CollisionService;

/**
 * Checks if there is any collision.
 */
public class CheckCollisionsUseCase {
    /**
     * Looks for collisions with food items.
     *
     * @param player the player to check
     * @param foods  food items
     * @return food items that player collides with
     */
    public List<Food> getFoodCollisions(Player player, List<Food> foods) {
        return foods.stream()
            .filter(f -> CollisionService.collides(player.snake().getHead(), f.position()))
            .toList();
    }

    /**
     * Checks if there is a dead collision. A dead collision for a snake is a collision with its
     * body, with board boarders or with other snakes.
     *
     * @param player       the player to check
     * @param rows         the number of rows on the game board
     * @param columns      the number of columns on the game board
     * @param otherPlayers other players on the board
     * @return {@code true} if there is a dead collision, {@code false} otherwise
     */
    public boolean isDeadCollision(
        Player player,
        int rows,
        int columns,
        List<Player> otherPlayers
    ) {
        return isBorderCollision(player, rows, columns)
            || isSelfCollision(player)
            || isOtherSnakeCollision(player, otherPlayers);
    }

    /**
     * Check if there is a self collision.
     *
     * @param player the player to check
     * @return {@code true} if there is a self collision, {@code false} otherwise
     */
    public boolean isSelfCollision(Player player) {
        var snake = player.snake();
        var head = snake.getHead();
        var body = snake.getBody();
        return CollisionService.collides(Set.copyOf(body), head);
    }

    /**
     * Checks if there is a collision with other snake.
     *
     * @param player       the player to check
     * @param otherPlayers other players on the board
     * @return {@code true} if there is a collision with another snake, {@code false} otherwise
     */
    public boolean isOtherSnakeCollision(Player player, List<Player> otherPlayers) {
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

    /**
     * Checks if there is a border collision.
     *
     * @param player  the player to check
     * @param rows    the number of rows on the game board
     * @param columns the number of columns on the game board
     * @return {@code true} if there is a border collision, {@code false} otherwise
     */
    public boolean isBorderCollision(Player player, int rows, int columns) {
        var head = player.snake().getHead();

        return head.x() < 0 || head.y() < 0 || head.x() >= rows || head.y() >= columns;
    }
}
