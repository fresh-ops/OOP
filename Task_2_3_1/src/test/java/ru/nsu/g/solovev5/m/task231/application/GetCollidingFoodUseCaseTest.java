package ru.nsu.g.solovev5.m.task231.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task231.domain.entities.Food;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

class GetCollidingFoodUseCaseTest {
    @Test
    void invoke_should_returnListOfCollidingFoodItems() {
        var player = new Player(new Snake(new Point2D(1, 1)), (head) -> head);
        var food = List.of(
            new Food(new Point2D(0, 0)),
            new Food(new Point2D(1, 2)),
            new Food(new Point2D(2, 0)),
            new Food(new Point2D(0, 2)),
            new Food(new Point2D(1, 1))
        );

        var useCase = new GetCollidingFoodUseCase();
        var collision = useCase.invoke(player, food);

        assertEquals(List.of(new Food(new Point2D(1, 1))), collision);
    }
}