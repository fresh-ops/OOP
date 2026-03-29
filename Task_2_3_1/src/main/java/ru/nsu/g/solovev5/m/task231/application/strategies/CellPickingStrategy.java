package ru.nsu.g.solovev5.m.task231.application.strategies;

import java.util.List;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

/**
 * A strategy for picking a cell from given.
 */
public interface CellPickingStrategy {
    /**
     * Picks a cell from given.
     *
     * @param cells a set of cells
     * @return a picked cell
     */
    Point2D pick(List<Point2D> cells);
}
