package ru.nsu.g.solovev5.m.task231.presentation.renderer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task231.application.transport.FoodRecord;
import ru.nsu.g.solovev5.m.task231.application.transport.SnakeRecord;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.FoodType;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;
import ru.nsu.g.solovev5.m.task231.presentation.renderer.figures.FigureType;

class GameRendererTest {
    GameRenderer renderer;

    @BeforeEach
    void setUp() {
        renderer = new GameRenderer();
    }

    @Test
    void render_should_createOneCircle_when_normalFoodIsPassed() {
        var food = new FoodRecord(
            new Point2D(2, 3),
            FoodType.NORMAL
        );

        var figures = renderer.render(food);

        assertEquals(1, figures.size());
        var figure = figures.get(0);
        assertEquals(FigureType.CIRCLE, figure.type());
    }

    @Test
    void render_should_createOneFigurePerSegment_when_snakeIsPassed() {
        var snake = new SnakeRecord(
            new Point2D(2, 3),
            new Point2D(2, 0),
            List.of(
                new Point2D(2, 3),
                new Point2D(2, 2),
                new Point2D(2, 1),
                new Point2D(2, 0)
            )
        );

        var figures = renderer.render(snake);
        assertEquals(snake.segments().size(), figures.size());
    }

    @Test
    void renderAll_should_renderAllObjects() {
        var food = List.of(
            new FoodRecord(new Point2D(2, 3), FoodType.NORMAL),
            new FoodRecord(new Point2D(0, 2), FoodType.NORMAL),
            new FoodRecord(new Point2D(1, 1), FoodType.NORMAL),
            new FoodRecord(new Point2D(3, 0), FoodType.NORMAL)
        );
        var snakes = List.of(
            new SnakeRecord(
                new Point2D(2, 3),
                new Point2D(2, 0),
                List.of(
                    new Point2D(2, 3),
                    new Point2D(2, 2),
                    new Point2D(2, 1),
                    new Point2D(2, 0)
                )
            )
        );

        var figures = renderer.renderAll(snakes, food);
        var snakesSegmentsCount = snakes.stream()
            .map(SnakeRecord::segments)
            .flatMap(List::stream)
            .count();
        assertEquals(food.size() + snakesSegmentsCount, figures.size());
    }
}