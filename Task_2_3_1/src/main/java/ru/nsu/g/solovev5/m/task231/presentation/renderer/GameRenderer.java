package ru.nsu.g.solovev5.m.task231.presentation.renderer;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import ru.nsu.g.solovev5.m.task231.application.dto.FoodDto;
import ru.nsu.g.solovev5.m.task231.application.dto.SnakeDto;
import ru.nsu.g.solovev5.m.task231.presentation.renderer.figures.Figure;
import ru.nsu.g.solovev5.m.task231.presentation.renderer.figures.FigureType;

/**
 * Object transforms game entities into drawable figures.
 */
public class GameRenderer {
    private static final double SNAKE_HEAD_SCALE = 0.75;
    private static final double SNAKE_BODY_SCALE = 0.55;
    private static final double SNAKE_TAIL_SCALE = 0.4;
    private static final double FOOD_SCALE = 0.8;

    /**
     * Renders all passed game entities.
     *
     * @param snakes alive snakes on the board
     * @param food   food items on the board
     * @return drawable figures
     */
    public List<Figure> renderAll(List<SnakeDto> snakes, List<FoodDto> food) {
        var figures = new ArrayList<Figure>();

        for (var snake : snakes) {
            figures.addAll(render(snake));
        }
        for (var f : food) {
            figures.addAll(render(f));
        }

        return figures;
    }

    /**
     * Renders the given food item.
     *
     * @param foodDto the food item to render
     * @return drawable figures
     */
    public List<Figure> render(FoodDto foodDto) {
        return List.of(
            new Figure(
                FigureType.CIRCLE,
                foodDto.position().y(),
                foodDto.position().x(),
                FOOD_SCALE,
                Color.RED
            )
        );
    }

    /**
     * Renders the given snake.
     *
     * @param snakeDto the snake on the board
     * @return drawable figures
     */
    public List<Figure> render(SnakeDto snakeDto) {
        List<Figure> figures = new ArrayList<>();
        for (var segment : snakeDto.segments()) {
            if (segment.equals(snakeDto.head()) || segment.equals(snakeDto.tail())) {
                continue;
            }

            figures.add(new Figure(
                FigureType.SQUARE,
                segment.y(),
                segment.x(),
                SNAKE_BODY_SCALE,
                Color.BLUE
            ));
        }

        figures.add(new Figure(
            FigureType.SQUARE,
            snakeDto.tail().y(),
            snakeDto.tail().x(),
            SNAKE_TAIL_SCALE,
            Color.BLUE
        ));

        figures.add(new Figure(
            FigureType.SQUARE,
            snakeDto.head().y(),
            snakeDto.head().x(),
            SNAKE_HEAD_SCALE,
            Color.DARKBLUE
        ));

        return figures;
    }
}
