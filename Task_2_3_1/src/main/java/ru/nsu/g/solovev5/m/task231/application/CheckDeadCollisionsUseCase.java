package ru.nsu.g.solovev5.m.task231.application;

import java.util.List;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;

/**
 * Checks if there is any collision.
 */
public class CheckDeadCollisionsUseCase {
    private final CheckBorderCollisionsUseCase checkBorderCollisionsUseCase;
    private final CheckSelfCollisionsUseCase checkSelfCollisionsUseCase;
    private final CheckEnemiesCollisions checkEnemiesCollisions;

    /**
     * Creates a new CheckDeadCollisionsUseCase.
     *
     * @param checkBorderCollisionsUseCase checks for border collisions
     * @param checkSelfCollisionsUseCase   checks for self collisions
     * @param checkEnemiesCollisions       checks for enemies collisions
     */
    public CheckDeadCollisionsUseCase(
        CheckBorderCollisionsUseCase checkBorderCollisionsUseCase,
        CheckSelfCollisionsUseCase checkSelfCollisionsUseCase,
        CheckEnemiesCollisions checkEnemiesCollisions
    ) {
        this.checkBorderCollisionsUseCase = checkBorderCollisionsUseCase;
        this.checkSelfCollisionsUseCase = checkSelfCollisionsUseCase;
        this.checkEnemiesCollisions = checkEnemiesCollisions;
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
    public boolean invoke(
        Player player,
        int rows,
        int columns,
        List<Player> otherPlayers
    ) {
        return checkBorderCollisionsUseCase.invoke(player, rows, columns)
            || checkSelfCollisionsUseCase.invoke(player)
            || checkEnemiesCollisions.invoke(player, otherPlayers);
    }
}
