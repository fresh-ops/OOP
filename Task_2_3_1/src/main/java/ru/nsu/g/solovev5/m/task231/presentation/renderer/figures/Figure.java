package ru.nsu.g.solovev5.m.task231.presentation.renderer.figures;

import javafx.scene.paint.Paint;

/**
 * A drawable figure.
 *
 * @param type   the type of the figure
 * @param row    the row to draw the figure in
 * @param column the column to draw the figure in
 * @param scale  the ratio of the figure size to the cell size
 * @param fill   the figure fill
 */
public record Figure(FigureType type, int row, int column, double scale, Paint fill) {
}
