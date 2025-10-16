package ru.nsu.solovev5.m.task121.matrix.exceptions;

/**
 * An exception occurred when trying to get access to matrix column with wrong row index.
 */
public class ColumnIndexOutOfBoundsException extends RuntimeException {
    /**
     * Constructs a new column index out of bounds exception.
     *
     * @param index a miss column index
     * @param columnCount the actual column number of matrix
     */
    public ColumnIndexOutOfBoundsException(int index, int columnCount) {
        super(String.format(
            "Column index %d out of bounds for matrix with %d columns",
            index, columnCount
        ));
    }
}
