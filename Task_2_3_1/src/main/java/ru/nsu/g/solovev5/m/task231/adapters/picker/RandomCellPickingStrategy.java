package ru.nsu.g.solovev5.m.task231.adapters.picker;

import java.util.List;
import java.util.Random;
import ru.nsu.g.solovev5.m.task231.application.strategies.CellPickingStrategy;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

public class RandomCellPickingStrategy implements CellPickingStrategy {
    private final Random random;

    public RandomCellPickingStrategy() {
        this.random = new Random();
    }

    @Override
    public Point2D pick(List<Point2D> cells) {
        var cellIndex = random.nextInt(cells.size());
        return cells.get(cellIndex);
    }
}
