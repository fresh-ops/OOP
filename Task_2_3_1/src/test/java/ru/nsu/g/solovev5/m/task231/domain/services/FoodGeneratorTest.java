package ru.nsu.g.solovev5.m.task231.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task231.domain.entities.Food;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.FoodType;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

class FoodGeneratorTest {
    @Test
    void generate_should_generateFoodInFreeCell() {
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
        var generator = new FoodGenerator(
            (cells) -> cells.get(0),
            () -> FoodType.NORMAL
        );

        var freeCells = generator.getFreeCells(rows, cols, players, food);
        var newFood = generator.generate(rows, cols, players, food);

        assertTrue(freeCells.contains(newFood.position()));
    }

    @Test
    void getFreeCells_should_returnOnlyFreeCells() {
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

        var generator = new FoodGenerator(
            (cells) -> cells.get(0),
            () -> FoodType.NORMAL
        );
        var freeCells = generator.getFreeCells(rows, cols, players, food);

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