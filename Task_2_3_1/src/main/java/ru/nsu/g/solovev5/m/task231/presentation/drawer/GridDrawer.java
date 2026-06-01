package ru.nsu.g.solovev5.m.task231.presentation.drawer;

import static ru.nsu.g.solovev5.m.task231.presentation.drawer.figures.FigureShapeCalculator.calculateShape;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ru.nsu.g.solovev5.m.task231.presentation.renderer.figures.Figure;
import ru.nsu.g.solovev5.m.task231.presentation.renderer.figures.FigureType;

/**
 * A view that draws items on the grid.
 */
public class GridDrawer extends BorderPane {
    private static final Paint DEFAULT_PRIMARY_COLOR = Color.YELLOWGREEN;
    private static final Paint DEFAULT_SECONDARY_COLOR = Color.FORESTGREEN;

    @FXML
    private Canvas canvas;

    private final int rows;
    private final int columns;
    private final double aspectRatio;

    private final Paint primaryColor;
    private final Paint secondaryColor;

    /**
     * Creates a new GridDrawer.
     *
     * @param rows           the number of rows in the grid
     * @param columns        the number of columns in the grid
     * @param primaryColor   the primary color of grid's cells
     * @param secondaryColor the secondary color of grid's cells
     * @throws IOException if drawer cannot locate its layout resource
     */
    public GridDrawer(
        int rows, int columns, Paint primaryColor, Paint secondaryColor
    ) throws IOException {
        this.rows = rows;
        this.columns = columns;
        aspectRatio = (double) columns / (double) rows;

        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;

        var loader = new FXMLLoader(
            getClass().getResource("grid-drawer.fxml")
        );
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            resizeCanvas(newValue);
        });
    }

    /**
     * Creates a new GridDrawer with the default color scheme.
     *
     * @param rows    the number of rows in the grid
     * @param columns the number of columns in the grid
     * @throws IOException if drawer cannot locate its layout resource
     */
    public GridDrawer(int rows, int columns) throws IOException {
        this(rows, columns, DEFAULT_PRIMARY_COLOR, DEFAULT_SECONDARY_COLOR);
    }

    /**
     * Draws a figure in the grid's cell.
     *
     * @param figure a figure to draw
     */
    public void drawFigure(Figure figure) {
        var shape = calculateShape(getCellSide(), figure);
        var graphics = canvas.getGraphicsContext2D();

        graphics.setFill(figure.fill());
        switch (figure.type()) {
            case CIRCLE -> graphics.fillOval(shape.x(), shape.y(), shape.width(), shape.height());
            default -> graphics.fillRect(shape.x(), shape.y(), shape.width(), shape.height());
        }
    }

    /**
     * Draws a board on the canvas.
     */
    public void drawBoard() {
        fillCanvas(primaryColor);
        for (var row = 0; row < rows; row++) {
            for (var column = (row + 1) & 1; column < columns; column += 2) {
                drawFigure(new Figure(
                    FigureType.SQUARE,
                    row, column, 1,
                    secondaryColor
                ));
            }
        }
    }

    /**
     * Clears whole canvas.
     */
    public void clearCanvas() {
        var graphics = canvas.getGraphicsContext2D();
        graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Resizes the inner canvas to fit bounds.
     *
     * @param bounds new canvas bounds
     */
    private void resizeCanvas(Bounds bounds) {
        var widthCandidate = bounds.getHeight() * aspectRatio;
        var heightCandidate = bounds.getWidth() / aspectRatio;

        if (widthCandidate > bounds.getWidth()) {
            canvas.setWidth(bounds.getWidth());
            canvas.setHeight(heightCandidate);
        } else {
            canvas.setWidth(widthCandidate);
            canvas.setHeight(bounds.getHeight());
        }
    }

    /**
     * Fills the canvas with the specified paint.
     *
     * @param fill the paint to fill with
     */
    private void fillCanvas(Paint fill) {
        var graphics = canvas.getGraphicsContext2D();
        graphics.setFill(fill);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Returns the side of a grid's cell.
     *
     * @return the side of a grid's cell
     */
    private double getCellSide() {
        return canvas.getWidth() / (double) columns;
    }
}
