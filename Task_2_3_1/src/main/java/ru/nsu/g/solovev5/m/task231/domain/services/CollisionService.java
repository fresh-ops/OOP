package ru.nsu.g.solovev5.m.task231.domain.services;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

/**
 * Util class to check collisions between points.
 */
public class CollisionService {
    private CollisionService() {
    }

    /**
     * Checks if there is a dead collision. A dead collision for a snake is a collision with its
     * body, with board boarders or with other snakes.
     *
     * @param player  the player to check
     * @param rows    the number of rows on the game board
     * @param columns the number of columns on the game board
     * @param enemies other players on the board
     * @return {@code true} if there is a dead collision, {@code false} otherwise
     */
    public static boolean deadCollision(
        Player player, int rows, int columns, List<Player> enemies
    ) {
        return outOfBounds(player, rows, columns)
            || selfCollision(player)
            || enemyCollision(player, enemies);
    }

    /**
     * Checks if player is out of border bounds.
     *
     * @param player  the player to check
     * @param rows    the number of rows on the game board
     * @param columns the number of columns on the game board
     * @return {@code true} if player is out of bounds, {@code false} otherwise
     */
    public static boolean outOfBounds(Player player, int rows, int columns) {
        Objects.requireNonNull(player);
        var head = player.snake().getHead();

        return head.x() < 0 || head.y() < 0 || head.x() >= columns || head.y() >= rows;
    }

    /**
     * Check if there is a self collision.
     *
     * @param player the player to check
     * @return {@code true} if there is a self collision, {@code false} otherwise
     */
    public static boolean selfCollision(Player player) {
        Objects.requireNonNull(player);
        var head = player.snake().getHead();
        var body = player.snake().getBody();

        return collides(Set.copyOf(body), head);
    }

    /**
     * Checks if there is a collision with other snake.
     *
     * @param player  the player to check
     * @param enemies other players on the board
     * @return {@code true} if there is a collision with another snake, {@code false} otherwise
     */
    public static boolean enemyCollision(Player player, List<Player> enemies) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(enemies);
        var head = player.snake().getHead();

        return enemies.stream()
            .filter(Objects::nonNull)
            .filter(e -> !e.equals(player))
            .map(Player::snake)
            .map(Snake::getSegments)
            .flatMap(List::stream)
            .anyMatch(point -> point.equals(head));
    }

    /**
     * Checks if points collide.
     *
     * @param a the first point to check
     * @param b the second point to check
     * @return {@code true} if points collide, {@code false} otherwise
     */
    public static boolean collides(Point2D a, Point2D b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        return a.equals(b);
    }

    /**
     * Checks if points collide.
     *
     * @param points the set of points
     * @param point  the point to check
     * @return {@code true} if the point collides the set, {@code false} otherwise
     */
    public static boolean collides(Set<Point2D> points, Point2D point) {
        Objects.requireNonNull(points);
        Objects.requireNonNull(point);
        return points.contains(point);
    }

    /**
     * Checks if points collide.
     *
     * @param a the set of points to check
     * @param b the set of points to check
     * @return {@code true} if sets collide each other, {@code false} otherwise
     */
    public static boolean collides(Set<Point2D> a, Set<Point2D> b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        return !Collections.disjoint(a, b);
    }
}
