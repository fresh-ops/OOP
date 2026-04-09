package ru.nsu.g.solovev5.m.task231.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task231.domain.entities.Food;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

class CheckCollisionsUseCaseTest {
    @Test
    void getFoodCollisions_should_returnListOfCollidingFoodItems() {
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);
        var food = List.of(
            new Food(new Point2D(0, 0)),
            new Food(new Point2D(1, 2)),
            new Food(new Point2D(2, 0)),
            new Food(new Point2D(0, 2)),
            new Food(new Point2D(1, 1))
        );

        var useCase = new CheckCollisionsUseCase();
        var collision = useCase.getFoodCollisions(player, food);

        assertEquals(List.of(new Food(new Point2D(1, 1))), collision);
    }

    @Test
    void isSelfCollision_should_returnTrue_when_thereIsSelfCollision() {
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);
        player.snake().addGrowthTicks(1);
        player.snake().move(new Point2D(1, 1));

        var useCase = new CheckCollisionsUseCase();

        assertTrue(useCase.isSelfCollision(player));
    }

    @Test
    void isSelfCollision_should_returnFalse_when_thereIsNoSelfCollision() {
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);

        var useCase = new CheckCollisionsUseCase();

        assertFalse(useCase.isSelfCollision(player));
    }

    @Test
    void isBorderCollision_should_returnTrue_when_thereIsBorderCollision() {
        int rows = 3;
        int columns = 3;
        var player = new Player(new Snake(new Point2D(-1, -1)), (head) -> head);

        var useCase = new CheckCollisionsUseCase();

        assertTrue(useCase.isBorderCollision(player, rows, columns));
    }

    @Test
    void isBorderCollision_should_returnFalse_when_thereIsNoBorderCollision() {
        int rows = 3;
        int columns = 3;
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);

        var useCase = new CheckCollisionsUseCase();

        assertFalse(useCase.isBorderCollision(player, rows, columns));
    }

    @Test
    void isOtherSnakeCollision_should_returnTrue_when_thereIsOtherSnakeCollision() {
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);
        var otherPlayers = List.of(
            new Player(new Snake(new Point2D(1, 0)), (head) -> head),
            new Player(new Snake(new Point2D(1, 1)), (head) -> head),
            new Player(new Snake(new Point2D(0, 1)), (head) -> head)
        );

        var useCase = new CheckCollisionsUseCase();

        assertTrue(useCase.isOtherSnakeCollision(player, otherPlayers));
    }

    @Test
    void isOtherSnakeCollision_should_returnFalse_when_thereIsNoOtherSnakeCollision() {
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);
        var otherPlayers = List.of(
            new Player(new Snake(new Point2D(1, 0)), (head) -> head),
            new Player(new Snake(new Point2D(1, 2)), (head) -> head),
            new Player(new Snake(new Point2D(0, 1)), (head) -> head)
        );

        var useCase = new CheckCollisionsUseCase();

        assertFalse(useCase.isOtherSnakeCollision(player, otherPlayers));
    }

    @Test
    void isOtherSnakeCollision_shouldNot_findCollisionWithPlayerItself() {
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);

        var useCase = new CheckCollisionsUseCase();

        assertFalse(useCase.isOtherSnakeCollision(player, List.of(player)));
    }

    @Test
    void isDeadCollision_should_beEqualToCollisionCombination() {
        int rows = 3;
        int columns = 3;
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);
        var otherPlayers = List.of(
            new Player(new Snake(new Point2D(1, 0)), (head) -> head),
            new Player(new Snake(new Point2D(1, 2)), (head) -> head),
            new Player(new Snake(new Point2D(0, 1)), (head) -> head)
        );

        var useCase = new CheckCollisionsUseCase();
        assertEquals(
            useCase.isBorderCollision(player, rows, columns)
                || useCase.isSelfCollision(player)
                || useCase.isOtherSnakeCollision(player, otherPlayers),
            useCase.isDeadCollision(player, rows, columns, otherPlayers)
        );
    }
}