package ru.nsu.g.solovev5.m.task231.presentation.renderer;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import ru.nsu.g.solovev5.m.task231.application.transport.FoodRecord;
import ru.nsu.g.solovev5.m.task231.application.transport.SnakeRecord;
import ru.nsu.g.solovev5.m.task231.presentation.renderer.figures.Figure;
import ru.nsu.g.solovev5.m.task231.presentation.renderer.figures.FigureType;

public class GameRenderer {
    private static final double SNAKE_HEAD_SCALE = 0.75;
    private static final double SNAKE_BODY_SCALE = 0.55;
    private static final double SNAKE_TAIL_SCALE = 0.4;
    private static final double FOOD_SCALE = 0.8;

    public List<Figure> renderAll(List<SnakeRecord> snakes, List<FoodRecord> food) {
        var figures = new ArrayList<Figure>();

        for (var snake : snakes) {
            figures.addAll(render(snake));
        }
        for (var f : food) {
            figures.addAll(render(f));
        }

        return figures;
    }

    public List<Figure> render(FoodRecord foodRecord) {
        return List.of(
            new Figure(
                FigureType.CIRCLE,
                foodRecord.position().y(),
                foodRecord.position().x(),
                FOOD_SCALE,
                Color.RED
            )
        );
    }

    public List<Figure> render(SnakeRecord snakeRecord) {
        List<Figure> figures = new ArrayList<>();
        for (var segment : snakeRecord.segments()) {
            if (segment.equals(snakeRecord.head()) || segment.equals(snakeRecord.tail())) {
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
            snakeRecord.tail().y(),
            snakeRecord.tail().x(),
            SNAKE_TAIL_SCALE,
            Color.BLUE
        ));

        figures.add(new Figure(
            FigureType.SQUARE,
            snakeRecord.head().y(),
            snakeRecord.head().x(),
            SNAKE_HEAD_SCALE,
            Color.DARKBLUE
        ));

        return figures;
    }
}
