package ru.nsu.solovev5.m.task121.matrix;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.solovev5.m.task121.matrix.exceptions.ColumnIndexOutOfBoundsException;
import ru.nsu.solovev5.m.task121.matrix.exceptions.RowIndexOutOfBoundsException;

class MatrixTest {
    Matrix<Integer> matrix;

    @BeforeEach
    void setUp() {
        matrix = new Matrix<>();
    }

    @Test
    void checkAddColumn() {
        assertEquals(
            0, matrix.columnsNumber(),
            "A new matrix should have no columns"
        );

        for (var i = 1; i < 10; i++) {
            matrix.addColumn();
            assertEquals(
                i, matrix.columnsNumber(),
                "Adding a column should increase the columns number"
            );
            assertTrue(
                matrix.isValidColumn(i - 1),
                "After adding the column should be valid"
            );
        }
    }

    @Test
    void checkAddRow() {
        assertEquals(
            0, matrix.rowsNumber(),
            "A new matrix should have no rows"
        );

        for (var i = 1; i < 10; i++) {
            matrix.addRow();
            assertEquals(
                i, matrix.rowsNumber(),
                "Adding a column should increase the rows number"
            );
            assertTrue(
                matrix.isValidRow(i - 1),
                "After adding the row should be valid"
            );
        }
    }

    @ParameterizedTest
    @MethodSource
    void checkGetValidElement(String message, int rowsNumber, int columnsNumber,
                              int itemRow, int itemColumn) {
        for (var i = 0; i < columnsNumber; i++) {
            matrix.addColumn();
        }
        for (var i = 0; i < rowsNumber; i++) {
            matrix.addRow();
        }

        assertTrue(
            matrix.isValidColumn(itemColumn),
            message + ". The column index should be valid"
        );
        assertTrue(
            matrix.isValidRow(itemRow),
            message + ". The row index should be valid"
        );
        assertTrue(
            matrix.isValidCell(itemRow, itemColumn),
            message + ". If the row and column indexes are valid," +
                " then the cell address should be valid too"
        );
        assertNull(
            matrix.get(itemRow, itemColumn),
            message + ". Unspecified element should be null"
        );
    }

    static Stream<Arguments> checkGetValidElement() {
        return Stream.of(
            Arguments.of("A matrix 1 by 1", 1, 1, 0, 0),
            Arguments.of("The last valid row index", 10, 12, 9, 4),
            Arguments.of("The last valid column index", 7, 5, 3, 4),
            Arguments.of("The first valid row index", 8, 19, 0, 4),
            Arguments.of("The first valid column index", 13, 9, 3, 8),
            Arguments.of("The valid element from the middle", 15, 15, 4, 9)
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkGetInvalidElement(String message,
                                Class<? extends Exception> exception,
                                int rowsNumber, int columnsNumber,
                                int itemRow, int itemColumn) {
        for (var i = 0; i < columnsNumber; i++) {
            matrix.addColumn();
        }
        for (var i = 0; i < rowsNumber; i++) {
            matrix.addRow();
        }

        assertFalse(
            matrix.isValidRow(itemRow) && matrix.isValidColumn(itemColumn),
            message + ". The row index or the column index shouldn't be valid"
        );
        assertFalse(
            matrix.isValidCell(itemRow, itemColumn),
            message + ". If the row or the column index is invalid," +
                " then the cell address should be invalid too"
        );
        assertThrows(
            exception,
            () -> matrix.get(itemRow, itemColumn),
            message + ". Unspecified element should be null"
        );
    }

    static Stream<Arguments> checkGetInvalidElement() {
        return Stream.of(
            Arguments.of("The negative row index",
                RowIndexOutOfBoundsException.class, 10, 12, -9, 4),
            Arguments.of("The negative column index",
                ColumnIndexOutOfBoundsException.class, 7, 5, 3, -4),
            Arguments.of("Too big row index",
                RowIndexOutOfBoundsException.class, 8, 19, 10, 4),
            Arguments.of("Too big column index",
                ColumnIndexOutOfBoundsException.class, 13, 9, 3, 28)
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkSetValidItem(String message, int rowsNumber, int columnsNumber,
                           int itemRow, int itemColumn, int item) {
        for (var i = 0; i < columnsNumber; i++) {
            matrix.addColumn();
        }
        for (var i = 0; i < rowsNumber; i++) {
            matrix.addRow();
        }

        assertNull(
            matrix.get(itemRow, itemColumn),
            message + ". The item should be null before setting"
        );
        assertDoesNotThrow(
            () -> matrix.set(itemRow, itemColumn, item),
            message + ". Setting item on valid address should not throw"
                + " any exception"
        );
        assertEquals(
            item, matrix.get(itemRow, itemColumn),
            message + ". The element should be set"
        );
    }

    static Stream<Arguments> checkSetValidItem() {
        return Stream.of(
            Arguments.of("A matrix 1 by 1", 1, 1, 0, 0, 8),
            Arguments.of("The last valid row index", 10, 12, 9, 4, 12),
            Arguments.of("The last valid column index", 7, 5, 3, 4, -100),
            Arguments.of("The first valid row index", 8, 19, 0, 4, 256),
            Arguments.of("The first valid column index", 13, 9, 3, 8, 37),
            Arguments.of("The valid element from the middle", 15, 15, 4, 9, 73)
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkSetInvalidItem(String message,
                             Class<? extends Exception> exception,
                             int rowsNumber, int columnsNumber,
                             int itemRow, int itemColumn, int item) {
        for (var i = 0; i < columnsNumber; i++) {
            matrix.addColumn();
        }
        for (var i = 0; i < rowsNumber; i++) {
            matrix.addRow();
        }

        assertThrows(
            exception,
            () -> matrix.set(itemRow, itemColumn, item),
            message + ". Setting element with invalid address should cause an"
                + "exception"
        );
    }

    static Stream<Arguments> checkSetInvalidItem() {
        return Stream.of(
            Arguments.of("The negative row index",
                RowIndexOutOfBoundsException.class, 10, 12, -9, 4, 48),
            Arguments.of("The negative column index",
                ColumnIndexOutOfBoundsException.class, 7, 5, 3, -4, 52),
            Arguments.of("Too big row index",
                RowIndexOutOfBoundsException.class, 8, 19, 10, 4, -101),
            Arguments.of("Too big column index",
                ColumnIndexOutOfBoundsException.class, 13, 9, 3, 28, 42)
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkRemoveValidRow(String message, int rowsNumber, int columnsNumber,
                                    int row) {
        for (var i = 0; i < columnsNumber; i++) {
            matrix.addColumn();
        }
        for (var i = 0; i < rowsNumber; i++) {
            matrix.addRow();
        }

        assertDoesNotThrow(
            () -> matrix.removeRow(row),
            message + ". Removing a valid row shouldn't cause any exception"
        );
        assertEquals(
            rowsNumber - 1, matrix.rowsNumber(),
            message + ". Removing a row should decrease the rows number"
        );
    }

    static Stream<Arguments> checkRemoveValidRow() {
        return Stream.of(
            Arguments.of("A matrix 1 by 1", 1, 1, 0),
            Arguments.of("The last valid row", 10, 12, 9),
            Arguments.of("The first valid row", 8, 19, 0),
            Arguments.of("The valid row from the middle", 15, 15, 4)
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkRemoveValidColumn(String message, int rowsNumber, int columnsNumber,
                                int column) {
        for (var i = 0; i < columnsNumber; i++) {
            matrix.addColumn();
        }
        for (var i = 0; i < rowsNumber; i++) {
            matrix.addRow();
        }

        assertDoesNotThrow(
            () -> matrix.removeColumn(column),
            message + ". Removing a valid column shouldn't cause any exception"
        );
        assertEquals(
            columnsNumber - 1, matrix.columnsNumber(),
            message + ". Removing a column should decrease the columns number"
        );
    }

    static Stream<Arguments> checkRemoveValidColumn() {
        return Stream.of(
            Arguments.of("A matrix 1 by 1", 1, 1, 0),
            Arguments.of("The last valid column", 10, 12, 11),
            Arguments.of("The first valid column", 8, 19, 0),
            Arguments.of("The valid column from the middle", 15, 15, 9)
        );
    }


    @ParameterizedTest
    @MethodSource
    void checkRemoveInvalidRow(String message, int rowsNumber, int columnsNumber,
                                int row) {
        for (var i = 0; i < columnsNumber; i++) {
            matrix.addColumn();
        }
        for (var i = 0; i < rowsNumber; i++) {
            matrix.addRow();
        }

        assertThrows(
            RowIndexOutOfBoundsException.class,
            () -> matrix.removeRow(row),
            message + ". Removing a row with invalid index should throw "
                + "an exception"
        );
        assertEquals(
            rowsNumber, matrix.rowsNumber(),
            message + ". Failed row removing shouldn't change the rows number"
        );
    }

    static Stream<Arguments> checkRemoveInvalidRow() {
        return Stream.of(
            Arguments.of("The negative row index", 10, 12, -9),
            Arguments.of("Too big row index", 8, 19, 10)
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkRemoveInvalidColumn(String message, int rowsNumber, int columnsNumber,
                               int column) {
        for (var i = 0; i < columnsNumber; i++) {
            matrix.addColumn();
        }
        for (var i = 0; i < rowsNumber; i++) {
            matrix.addRow();
        }

        assertThrows(
            ColumnIndexOutOfBoundsException.class,
            () -> matrix.removeColumn(column),
            message + ". Removing a column with invalid index should throw "
                + "an exception"
        );
        assertEquals(
            columnsNumber, matrix.columnsNumber(),
            message + ". Failed column removing shouldn't change the columns number"
        );
    }

    static Stream<Arguments> checkRemoveInvalidColumn() {
        return Stream.of(
            Arguments.of("The negative column index", 10, 12, -1),
            Arguments.of("Too big column index", 8, 19, 20)
        );
    }
}