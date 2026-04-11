package ru.nsu.g.solovev5.m.task231.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

class CheckBorderCollisionsUseCaseTest {
    CheckBorderCollisionsUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CheckBorderCollisionsUseCase();
    }

    @Test
    void invoke_should_returnTrue_when_yIsOutOfRows() {
        int rows = 3;
        int columns = 3;
        var player = new Player(new Snake(new Point2D(1, 4)), (head) -> head);

        assertTrue(useCase.invoke(player, rows, columns));
    }


    @Test
    void invoke_should_returnTrue_when_yIsNegative() {
        int rows = 3;
        int columns = 3;
        var player = new Player(new Snake(new Point2D(1, -1)), (head) -> head);

        assertTrue(useCase.invoke(player, rows, columns));
    }

    @Test
    void invoke_should_returnTrue_when_xIsOutOfColumns() {
        int rows = 3;
        int columns = 3;
        var player = new Player(new Snake(new Point2D(4, 1)), (head) -> head);

        assertTrue(useCase.invoke(player, rows, columns));
    }


    @Test
    void invoke_should_returnTrue_when_xIsNegative() {
        int rows = 3;
        int columns = 3;
        var player = new Player(new Snake(new Point2D(-1, 1)), (head) -> head);

        assertTrue(useCase.invoke(player, rows, columns));
    }

    @Test
    void invoke_should_returnFalse_when_thereIsNoBorderCollision() {
        int rows = 3;
        int columns = 3;
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);

        assertFalse(useCase.invoke(player, rows, columns));
    }

}