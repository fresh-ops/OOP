package ru.nsu.g.solovev5.m.task231.presentation.board;

/**
 * Calculates rectangles' shapes.
 */
public class RectangleShapeCalculator {
    /**
     * Calculates rectangle's shape.
     *
     * @param gridWidth   the grid width
     * @param gridHeight  the grid height
     * @param columns     the columns in the grid
     * @param rows        the rows in the grid
     * @param columnIndex the column index
     * @param rowIndex    the row index
     * @param scale       desired scale
     * @return rectangle's shape
     */
    public static RectangleShape calculate(
        double gridWidth, double gridHeight,
        int columns, int rows,
        int columnIndex, int rowIndex,
        double scale
    ) {
        var columnWidth = gridWidth / columns;
        var rowHeight = gridHeight / rows;

        var squareSize = Math.min(columnWidth, rowHeight) * scale;

        var horizontalOffset = (columnWidth - squareSize) / 2;
        var verticalOffset = (rowHeight - squareSize) / 2;

        var x = horizontalOffset + columnIndex * columnWidth;
        var y = verticalOffset + rowIndex * rowHeight;

        return new RectangleShape(x, y, squareSize, squareSize);
    }
}
