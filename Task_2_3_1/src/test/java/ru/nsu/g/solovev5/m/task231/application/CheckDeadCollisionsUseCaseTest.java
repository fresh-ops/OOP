package ru.nsu.g.solovev5.m.task231.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

class CheckDeadCollisionsUseCaseTest {
    CheckDeadCollisionsUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CheckDeadCollisionsUseCase(
            new CheckBorderCollisionsUseCase(),
            new CheckSelfCollisionsUseCase(),
            new CheckEnemiesCollisions()
        );
    }

    @Test
    void invoke_should_returnCombinationOfPassedCheckers() {
        int rows = 3;
        int columns = 3;
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);
        var otherPlayers = List.of(
            new Player(new Snake(new Point2D(1, 0)), (head) -> head),
            new Player(new Snake(new Point2D(1, 2)), (head) -> head),
            new Player(new Snake(new Point2D(0, 1)), (head) -> head)
        );

        var borderCollisions = new CheckBorderCollisionsUseCase();
        var selfCollisions = new CheckSelfCollisionsUseCase();
        var enemiesCollisions = new CheckEnemiesCollisions();

        assertEquals(
            borderCollisions.invoke(player, rows, columns)
                || selfCollisions.invoke(player)
                || enemiesCollisions.invoke(player, otherPlayers),
            useCase.invoke(player, rows, columns, otherPlayers)
        );
    }
}