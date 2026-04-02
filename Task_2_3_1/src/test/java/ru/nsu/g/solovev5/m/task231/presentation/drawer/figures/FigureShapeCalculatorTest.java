package ru.nsu.g.solovev5.m.task231.presentation.drawer.figures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.nsu.g.solovev5.m.task231.presentation.drawer.figures.FigureShapeCalculator.calculateShape;

import org.junit.jupiter.api.Test;

class FigureShapeCalculatorTest {
    @Test
    void calculateShape_should_returnShapeWithEqualWidthAndHeight() {
        var shape = calculateShape(100.0, new Figure(
            FigureType.SQUARE,
            0, 0, 1,
            null
        ));

        assertEquals(shape.width(), shape.height());
    }

    @Test
    void calculateShape_should_calculateSizeCorrectly() {
        var shape = calculateShape(100.0, new Figure(
            FigureType.SQUARE,
            0, 0, 0.75,
            null
        ));

        assertEquals(0.75, shape.width() / 100.0);
    }
}