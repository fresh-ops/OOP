package ru.nsu.solovev5.m.task121.matrix;

import java.util.ArrayList;
import ru.nsu.solovev5.m.task121.matrix.exceptions.ColumnIndexOutOfBoundsException;
import ru.nsu.solovev5.m.task121.matrix.exceptions.RowIndexOutOfBoundsException;

/**
 * Represents a dynamic matrix.
 *
 * @param <E> the type of elements in this matrix
 */
public class Matrix<E> {
    private final ArrayList<ArrayList<E>> matrix;
    private int rowsNumber;
    private int columnsNumber;

    /**
     * Constructs a new empty matrix.
     */
    public Matrix() {
        matrix = new ArrayList<>();
        rowsNumber = columnsNumber = 0;
    }

    /**
     * Sets a specific cell of this matrix.
     *
     * @param row    a row index
     * @param column a column index
     * @param item   an item to put in the cell
     */
    public void set(int row, int column, E item) {
        if (!isValidRow(row)) {
            throw new RowIndexOutOfBoundsException(row, rowsNumber);
        }
        if (!isValidColumn(column)) {
            throw new ColumnIndexOutOfBoundsException(column, columnsNumber);
        }

        matrix.get(row).set(column, item);
    }

    /**
     * Returns a value of a specific cell of this matrix.
     *
     * @param row    a row index
     * @param column a column index
     * @return a matrix item
     */
    public E get(int row, int column) {
        if (!isValidRow(row)) {
            throw new RowIndexOutOfBoundsException(row, rowsNumber);
        }
        if (!isValidColumn(column)) {
            throw new ColumnIndexOutOfBoundsException(column, columnsNumber);
        }
        return matrix.get(row).get(column);
    }

    /**
     * Returns a rows number in this matrix.
     *
     * @return a rows number
     */
    public int rowsNumber() {
        return rowsNumber;
    }

    /**
     * Returns a columns number in this matrix.
     *
     * @return a columns number
     */
    public int columnsNumber() {
        return columnsNumber;
    }

    /**
     * Checks if the given row index is valid for this matrix.
     *
     * @param row a row index
     * @return {@code true} if row index is valid, {@code false} otherwise
     */
    public boolean isValidRow(int row) {
        return 0 <= row && row < rowsNumber;
    }

    /**
     * Checks if the given column index is valid for this matrix.
     *
     * @param column a column index
     * @return {@code true} if column index is valid, {@code false} otherwise
     */
    public boolean isValidColumn(int column) {
        return 0 <= column && column < columnsNumber;
    }

    /**
     * Combines {@link #isValidRow(int)} and {@link #isValidColumn(int)}.
     *
     * @param row    a row index
     * @param column a column index
     * @return {@code true} if given index is valid for this matrix,
     * {@code false} otherwise
     */
    public boolean isValidCell(int row, int column) {
        return isValidRow(row) && isValidColumn(column);
    }

    /**
     * Adds a row in the end of this matrix. Increases the row number.
     * A new row is columnNumber length and filled with null by default.
     */
    public void addRow() {
        matrix.add(new ArrayList<>(columnsNumber));

        for (var i = 0; i < columnsNumber; i++) {
            matrix.get(rowsNumber).add(null);
        }

        rowsNumber++;
    }

    /**
     * Adds a column in the end of this matrix. Increases the column number.
     * A new column is rowNumber length and filled with null by default.
     */
    public void addColumn() {
        columnsNumber++;

        for (ArrayList<E> row : matrix) {
            row.add(null);
        }
    }

    /**
     * Removes a specific row from this matrix.
     *
     * @param row a row index
     * @return the row that was removed from this matrix
     * @throws RowIndexOutOfBoundsException if the row index is out of range
     */
    public ArrayList<E> removeRow(int row) {
        if (!isValidRow(row)) {
            throw new RowIndexOutOfBoundsException(row, rowsNumber);
        }

        rowsNumber--;
        return matrix.remove(row);
    }

    /**
     * Removes a specific column from this matrix.
     *
     * @param column a column index
     * @return the column that was removed from this matrix
     * @throws ColumnIndexOutOfBoundsException if the column index is
     *                                         out of range
     */
    public ArrayList<E> removeColumn(int column) {
        if (!isValidColumn(column)) {
            throw new ColumnIndexOutOfBoundsException(column, columnsNumber);
        }

        var result = new ArrayList<E>();
        for (ArrayList<E> row : matrix) {
            result.add(row.remove(column));
        }

        columnsNumber--;
        return result;
    }
}
