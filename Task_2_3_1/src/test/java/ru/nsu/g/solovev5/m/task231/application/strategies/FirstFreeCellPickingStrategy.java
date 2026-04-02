package ru.nsu.g.solovev5.m.task231.application.strategies;

import java.util.List;
import ru.nsu.g.solovev5.m.task231.application.exceptions.NoFreeCellsException;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

/**
 * Picks the first free cell.
 */
public class FirstFreeCellPickingStrategy implements CellPickingStrategy {
    @Override
    public Point2D pick(List<Point2D> cells) {
        if (cells.isEmpty()) {
            throw new NoFreeCellsException();
        }

        return cells.get(0);
    }
}
