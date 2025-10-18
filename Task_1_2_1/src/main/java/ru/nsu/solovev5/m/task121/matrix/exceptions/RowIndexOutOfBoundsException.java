package ru.nsu.solovev5.m.task121.matrix.exceptions;

/**
 * An exception occurred when trying to get access to matrix row with wrong row index.
 */
public class RowIndexOutOfBoundsException extends RuntimeException {
    /**
     * Constructs a new row index out of bounds exception.
     *
     * @param index    a miss row index
     * @param rowCount the actual row number of matrix
     */
    public RowIndexOutOfBoundsException(int index, int rowCount) {
        super(String.format(
            "Row index %d out of bounds for matrix with %d rows",
            index, rowCount
        ));
    }
}
