package ru.nsu.g.solovev5.m.task231.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task231.domain.entities.Food;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

class GetFreeCellsUseCaseTest {
    @Test
    void invoke_should_returnOnlyFreeCells() {
        int rows = 3;
        int cols = 3;
        var food = List.of(
            new Food(new Point2D(0, 0)),
            new Food(new Point2D(2, 0)),
            new Food(new Point2D(0, 2)),
            new Food(new Point2D(0, 1))
        );
        var players = List.of(
            new Player(new Snake(new Point2D(1, 1)), (head) -> head)
        );

        var useCase = new GetFreeCellsUseCase();
        var freeCells = useCase.invoke(rows, cols, players, food);

        assertEquals(
            List.of(
                new Point2D(1, 0),
                new Point2D(1, 2),
                new Point2D(2, 1),
                new Point2D(2, 2)
            ), freeCells
        );
    }
}