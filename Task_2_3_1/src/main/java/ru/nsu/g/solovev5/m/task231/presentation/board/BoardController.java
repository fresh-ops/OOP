package ru.nsu.g.solovev5.m.task231.presentation.board;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import ru.nsu.g.solovev5.m.task231.application.transport.GameStateRecord;

/**
 * A controller for the game board view.
 */
public class BoardController {
    @FXML
    private Canvas canvas;
    @FXML
    private StackPane root;
    private GraphicsContext graphicsContext;

    /**
     * Initializes this controller.
     */
    @FXML
    private void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        root.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            resizeCanvas(newValue.getWidth(), newValue.getHeight());
        });
    }

    /**
     * Renders the game state on the board.
     *
     * @param state the state to render
     */
    public synchronized void render(GameStateRecord state) {
        drawBoard(state.rows(), state.columns());
    }

    /**
     * Resizes canvas to fit the container.
     *
     * @param width  the new width of the container
     * @param height the new height of the container
     */
    private synchronized void resizeCanvas(double width, double height) {
        var sideSize = Math.min(width, height);
        canvas.setWidth(sideSize);
        canvas.setHeight(sideSize);
    }

    /**
     * Draws the game board.
     *
     * @param rows    the number of rows on the board
     * @param columns the number of the columns on the board
     */
    private void drawBoard(int rows, int columns) {
        graphicsContext.setFill(Color.FORESTGREEN);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        var cellWidth = canvas.getWidth() / columns;
        var cellHeight = canvas.getHeight() / rows;
        graphicsContext.setFill(Color.YELLOWGREEN);

        for (var row = 0; row < rows; row++) {
            for (var column = row % 2; column < columns; column += 2) {
                graphicsContext.fillRect(
                    column * cellWidth, row * cellHeight,
                    cellWidth, cellHeight
                );
            }
        }
    }
}
