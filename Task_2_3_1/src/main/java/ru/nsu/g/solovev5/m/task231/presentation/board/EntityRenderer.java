package ru.nsu.g.solovev5.m.task231.presentation.board;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import ru.nsu.g.solovev5.m.task231.application.transport.FoodRecord;
import ru.nsu.g.solovev5.m.task231.application.transport.SnakeRecord;

/**
 * Renders the game entity.
 */
public class EntityRenderer {
    /**
     * Renders a snake.
     *
     * @param snake   the snake to render
     * @param rows    the rows number on the board
     * @param columns the columns number on the board
     * @param width   the width of the board
     * @param height  the height of the board
     * @return drawable rectangles
     */
    public static List<Rectangle> render(
        SnakeRecord snake,
        int rows, int columns,
        double width, double height
    ) {
        var rectangles = new ArrayList<Rectangle>();

        for (var segment : snake.segments()) {
            if (segment.equals(snake.tail())) {
                continue;
            }
            var shape = RectangleShapeCalculator.calculate(
                width, height, columns, rows,
                segment.x(), segment.y(), 0.5
            );

            rectangles.add(new Rectangle(shape, Color.DARKBLUE));
        }

        var headShape = RectangleShapeCalculator.calculate(
            width, height,
            columns, rows,
            snake.head().x(), snake.head().y(),
            0.75
        );
        rectangles.add(new Rectangle(headShape, Color.DARKBLUE));

        var tailShape = RectangleShapeCalculator.calculate(
            width, height,
            columns, rows,
            snake.tail().x(), snake.tail().y(),
            0.25
        );
        rectangles.add(new Rectangle(tailShape, Color.DARKBLUE));

        return rectangles;
    }

    /**
     * Renders food items.
     *
     * @param foods   the food to render
     * @param rows    the rows number on the board
     * @param columns the columns number on the board
     * @param width   the width of the board
     * @param height  the height of the board
     * @return drawable rectangles
     */
    public static List<Rectangle> render(
        List<FoodRecord> foods,
        int rows, int columns,
        double width, double height
    ) {
        var rectangles = new ArrayList<Rectangle>();

        for (var food : foods) {
            var shape = RectangleShapeCalculator.calculate(
                width, height, columns, rows,
                food.position().x(), food.position().y(),
                0.8
            );
            rectangles.add(new Rectangle(shape, Color.RED));
        }

        return rectangles;
    }
}
