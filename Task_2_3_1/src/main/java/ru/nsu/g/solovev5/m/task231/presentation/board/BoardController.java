package ru.nsu.g.solovev5.m.task231.presentation.board;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class BoardController {
    @FXML
    private Canvas canvas;
    @FXML
    private StackPane root;
    private GraphicsContext graphicsContext;

    @FXML
    private void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        root.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            resizeCanvas(newValue.getWidth(), newValue.getHeight());
            draw();
        });

        rows.addListener((observable, oldValue, newValue) -> {
            draw();
        });
        columns.addListener((observable, oldValue, newValue) -> {
            draw();
        });
    }

    private final IntegerProperty rows = new SimpleIntegerProperty(1);
    private final IntegerProperty columns = new SimpleIntegerProperty(1);

    public void setRows(int rows) {
        this.rows.set(rows);
    }

    public void setColumns(int columns) {
        this.columns.set(columns);
    }

    private void resizeCanvas(double width, double height) {
        var sideSize = Math.min(width, height);
        canvas.setWidth(sideSize);
        canvas.setHeight(sideSize);
    }

    private void draw() {
        graphicsContext.setFill(Color.FORESTGREEN);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        var cellWidth = canvas.getWidth() / columns.get();
        var cellHeight = canvas.getHeight() / rows.get();
        graphicsContext.setFill(Color.YELLOWGREEN);

        for (var row = 0; row < rows.get(); row++) {
            for (var column = row % 2; column < columns.get(); column += 2) {
                graphicsContext.fillRect(
                    column * cellWidth, row * cellHeight,
                    cellWidth, cellHeight
                );
            }
        }
    }
}
