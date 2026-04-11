package ru.nsu.g.solovev5.m.task231.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

class CheckEnemiesCollisionsTest {
    CheckEnemiesCollisions useCase;

    @BeforeEach
    void setUp() {
        useCase = new CheckEnemiesCollisions();
    }

    @Test
    void isOtherSnakeCollision_should_returnTrue_when_thereIsOtherSnakeCollision() {
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);
        var otherPlayers = List.of(
            new Player(new Snake(new Point2D(1, 0)), (head) -> head),
            new Player(new Snake(new Point2D(1, 1)), (head) -> head),
            new Player(new Snake(new Point2D(0, 1)), (head) -> head)
        );

        assertTrue(useCase.invoke(player, otherPlayers));
    }

    @Test
    void isOtherSnakeCollision_should_returnFalse_when_thereIsNoOtherSnakeCollision() {
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);
        var otherPlayers = List.of(
            new Player(new Snake(new Point2D(1, 0)), (head) -> head),
            new Player(new Snake(new Point2D(1, 2)), (head) -> head),
            new Player(new Snake(new Point2D(0, 1)), (head) -> head)
        );

        assertFalse(useCase.invoke(player, otherPlayers));
    }

    @Test
    void isOtherSnakeCollision_shouldNot_findCollisionWithPlayerItself() {
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);

        assertFalse(useCase.invoke(player, List.of(player)));
    }

}