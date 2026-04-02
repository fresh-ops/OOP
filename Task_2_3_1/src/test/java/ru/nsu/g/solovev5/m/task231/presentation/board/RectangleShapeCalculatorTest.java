package ru.nsu.g.solovev5.m.task231.presentation.board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RectangleShapeCalculatorTest {
    @Test
    void calculate_should_createSquares() {
        var rectangle = RectangleShapeCalculator.calculate(
            200, 300,
            2, 3,
            0, 0,
            1.0
        );

        assertEquals(
            rectangle.x(), rectangle.y()
        );
    }
}