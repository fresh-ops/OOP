package ru.nsu.g.solovev5.m.task231.application.strategies.cellpicking;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

class RandomRectangleShapePickingStrategyTest {
    @Test
    void pick_should_chooseCellFromGiven() {
        var picker = new RandomCellPickingStrategy();

        var cells = List.of(
            new Point2D(0, 0),
            new Point2D(0, 1),
            new Point2D(0, 2),
            new Point2D(4, 1),
            new Point2D(2, 1)
        );

        var picked = picker.pick(cells);

        assertTrue(cells.contains(picked));
    }
}