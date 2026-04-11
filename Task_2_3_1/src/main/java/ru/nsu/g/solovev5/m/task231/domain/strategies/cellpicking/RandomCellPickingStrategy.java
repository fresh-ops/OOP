package ru.nsu.g.solovev5.m.task231.domain.strategies.cellpicking;

import java.util.List;
import java.util.Random;
import ru.nsu.g.solovev5.m.task231.domain.exceptions.NoFreeCellsException;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

/**
 * Picks a cell using random.
 */
public class RandomCellPickingStrategy implements CellPickingStrategy {
    private final Random random;

    /**
     * Creates a new RandomCellPickingStrategy.
     */
    public RandomCellPickingStrategy() {
        this.random = new Random();
    }

    @Override
    public Point2D pick(List<Point2D> cells) {
        if (cells.isEmpty()) {
            throw new NoFreeCellsException();
        }
        var cellIndex = random.nextInt(cells.size());
        return cells.get(cellIndex);
    }
}
