package ru.nsu.g.solovev5.m.task231.presentation.drawer.figures;

import ru.nsu.g.solovev5.m.task231.presentation.renderer.figures.Figure;
import ru.nsu.g.solovev5.m.task231.presentation.renderer.figures.FigureShape;

/**
 * Util class to calculate figures shapes.
 */
public class FigureShapeCalculator {
    /**
     * Calculates the figure shape.
     *
     * @param cellSide the side of grid's cell
     * @param figure the figure to calculate shape of
     * @return the figure shape
     */
    public static FigureShape calculateShape(double cellSide, Figure figure) {
        var squareSide = cellSide * figure.scale();
        var offset = (cellSide - squareSide) / 2;

        return new FigureShape(
            offset + figure.column() * cellSide,
            offset + figure.row() * cellSide,
            squareSide, squareSide
        );
    }
}
