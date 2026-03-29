package ru.nsu.g.solovev5.m.task231.presentation.board;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import ru.nsu.g.solovev5.m.task231.application.transport.FoodRecord;
import ru.nsu.g.solovev5.m.task231.application.transport.GameStateRecord;
import ru.nsu.g.solovev5.m.task231.application.transport.SnakeRecord;

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
        for (var food : state.foods()) {
            drawFood(food, state.rows(), state.columns());
        }
        for (var snake : state.snakes()) {
            drawSnake(snake, state.rows(), state.columns());
        }
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

    /**
     * Draws the snake on the board.
     *
     * @param snake   the snake to draw
     * @param rows    the number of rows on the board
     * @param columns the number of columns on the board
     */
    private void drawSnake(SnakeRecord snake, int rows, int columns) {
        var cellWidth = canvas.getWidth() / columns;
        var cellHeight = canvas.getHeight() / rows;
        var segmentSize = Math.min(cellWidth, cellHeight) * 0.5;
        var horizontalOffset = (cellWidth - segmentSize) / 2;
        var verticalOffset = (cellHeight - segmentSize) / 2;

        graphicsContext.setFill(Color.DARKBLUE);

        for (var segment : snake.segments()) {
            if (segment.equals(snake.tail())) {
                continue;
            }
            var x = horizontalOffset + segment.x() * cellWidth;
            var y = verticalOffset + segment.y() * cellHeight;
            graphicsContext.fillRect(x, y, segmentSize, segmentSize);
        }
        var headSize = segmentSize * 1.5;
        var headHorizontalOffset = (cellWidth - headSize) / 2;
        var headVerticalOffset = (cellHeight - headSize) / 2;
        var headX = headHorizontalOffset + snake.head().x() * cellWidth;
        var headY = headVerticalOffset + snake.head().y() * cellHeight;
        graphicsContext.fillRect(headX, headY, headSize, headSize);

        var tailSize = segmentSize / 1.5;
        var tailHorizontalOffset = (cellWidth - tailSize) / 2;
        var tailVerticalOffset = (cellHeight - tailSize) / 2;
        var tailX = tailHorizontalOffset + snake.tail().x() * cellWidth;
        var tailY = tailVerticalOffset + snake.tail().y() * cellHeight;
        graphicsContext.fillRect(tailX, tailY, tailSize, tailSize);
    }

    private void drawFood(FoodRecord food, int rows, int columns) {
        var cellWidth = canvas.getWidth() / columns;
        var cellHeight = canvas.getHeight() / rows;
        var foodSize = Math.min(cellWidth, cellHeight) * 0.9;
        var horizontalOffset = (cellWidth - foodSize) / 2;
        var verticalOffset = (cellHeight - foodSize) / 2;

        graphicsContext.setFill(Color.RED);
        var x = horizontalOffset + food.position().x() * cellWidth;
        var y = verticalOffset + food.position().y() * cellHeight;
        graphicsContext.fillRect(x, y, foodSize, foodSize);
    }
}
