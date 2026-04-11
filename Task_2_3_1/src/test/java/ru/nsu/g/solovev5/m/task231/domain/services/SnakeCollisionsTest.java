package ru.nsu.g.solovev5.m.task231.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.nsu.g.solovev5.m.task231.domain.services.CollisionService.deadCollision;
import static ru.nsu.g.solovev5.m.task231.domain.services.CollisionService.enemyCollision;
import static ru.nsu.g.solovev5.m.task231.domain.services.CollisionService.outOfBounds;
import static ru.nsu.g.solovev5.m.task231.domain.services.CollisionService.selfCollision;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

class SnakeCollisionsTest {
    @Test
    void outOfBounds_should_returnTrue_when_yIsOutOfRows() {
        int rows = 3;
        int columns = 3;
        var player = new Player(new Snake(new Point2D(1, 3)), (head) -> head);

        assertTrue(outOfBounds(player, rows, columns));
    }

    @Test
    void outOfBounds_should_returnTrue_when_yIsNegative() {
        int rows = 3;
        int columns = 3;
        var player = new Player(new Snake(new Point2D(1, -1)), (head) -> head);

        assertTrue(outOfBounds(player, rows, columns));
    }

    @Test
    void outOfBounds_should_returnTrue_when_xIsOutOfColumns() {
        int rows = 3;
        int columns = 3;
        var player = new Player(new Snake(new Point2D(3, 1)), (head) -> head);

        assertTrue(outOfBounds(player, rows, columns));
    }

    @Test
    void outOfBounds_should_returnTrue_when_xIsNegative() {
        int rows = 3;
        int columns = 3;
        var player = new Player(new Snake(new Point2D(-1, 1)), (head) -> head);

        assertTrue(outOfBounds(player, rows, columns));
    }

    @Test
    void outOfBounds_should_returnFalse_when_thereIsNoBorderCollision() {
        int rows = 3;
        int columns = 3;
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);

        assertFalse(outOfBounds(player, rows, columns));
    }

    @Test
    void selfCollision_should_returnTrue_when_thereIsSelfCollision() {
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);
        player.snake().addGrowthTicks(1);
        player.snake().move(new Point2D(1, 1));

        assertTrue(selfCollision(player));
    }

    @Test
    void selfCollision_should_returnFalse_when_thereIsNoSelfCollision() {
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);

        assertFalse(selfCollision(player));
    }

    @Test
    void isOtherSnakeCollision_should_returnTrue_when_thereIsOtherSnakeCollision() {
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);
        var otherPlayers = List.of(
            new Player(new Snake(new Point2D(1, 0)), (head) -> head),
            new Player(new Snake(new Point2D(1, 1)), (head) -> head),
            new Player(new Snake(new Point2D(0, 1)), (head) -> head)
        );

        assertTrue(enemyCollision(player, otherPlayers));
    }

    @Test
    void enemyCollision_should_returnFalse_when_thereIsNoOtherSnakeCollision() {
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);
        var otherPlayers = List.of(
            new Player(new Snake(new Point2D(1, 0)), (head) -> head),
            new Player(new Snake(new Point2D(1, 2)), (head) -> head),
            new Player(new Snake(new Point2D(0, 1)), (head) -> head)
        );

        assertFalse(enemyCollision(player, otherPlayers));
    }

    @Test
    void enemyCollision_shouldNot_findCollisionWithPlayerItself() {
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);

        assertFalse(enemyCollision(player, List.of(player)));
    }

    @Test
    void deadCollision_should_beEqualToCombinationOfOtherCollisions() {
        int rows = 3;
        int columns = 3;
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);
        var otherPlayers = List.of(
            new Player(new Snake(new Point2D(1, 0)), (head) -> head),
            new Player(new Snake(new Point2D(1, 2)), (head) -> head),
            new Player(new Snake(new Point2D(0, 1)), (head) -> head)
        );

        assertEquals(
            outOfBounds(player, rows, columns)
                || selfCollision(player)
                || enemyCollision(player, otherPlayers),
            deadCollision(player, rows, columns, otherPlayers)
        );
    }
}