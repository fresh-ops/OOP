package ru.nsu.g.solovev5.m.task231.application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task231.domain.entities.Food;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.FoodType;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

class GenerateFoodUseCaseTest {
    @Test
    void invoke_should_generateFoodInFreeCell() {
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
        var getFreeCellsUseCase = new GetFreeCellsUseCase();
        var useCase = new GenerateFoodUseCase(
            getFreeCellsUseCase,
            (cells) -> cells.get(0),
            () -> FoodType.NORMAL
        );

        var freeCells = getFreeCellsUseCase.invoke(rows, cols, players, food);
        var newFood = useCase.invoke(rows, cols, players, food);

        assertTrue(freeCells.contains(newFood.position()));
    }
}