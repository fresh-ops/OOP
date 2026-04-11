package ru.nsu.g.solovev5.m.task231.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

class CheckSelfCollisionsUseCaseTest {
    CheckSelfCollisionsUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CheckSelfCollisionsUseCase();
    }

    @Test
    void isSelfCollision_should_returnTrue_when_thereIsSelfCollision() {
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);
        player.snake().addGrowthTicks(1);
        player.snake().move(new Point2D(1, 1));

        assertTrue(useCase.invoke(player));
    }

    @Test
    void isSelfCollision_should_returnFalse_when_thereIsNoSelfCollision() {
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);

        assertFalse(useCase.invoke(player));
    }

}