package ru.nsu.g.solovev5.m.task231.domain.snake;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;
import ru.nsu.g.solovev5.m.task231.domain.exceptions.SnakeZeroLengthException;

class SnakeTest {
    private static final int MAX_TESTING_LENGTH = 25;
    private static final int MIN_TESTING_LENGTH = 1;
    private static final int TESTING_LENGTH_STEP = 5;

    @Test
    void constructor_should_putHeadAtCoordinatesStart_when_calledWithoutParameters() {
        var snake = new Snake();
        var head = snake.getHead();

        assertEquals(
            new Point2D(0, 0), head,
            "The head should be exactly at the beginning of coordinates"
        );
        assertEquals(
            1, snake.getSegments().size(),
            "The new snake should have exactly one segment"
        );
    }

    @ParameterizedTest
    @MethodSource("generateCoordinates")
    void constructor_should_putHeadAtSpecifiedCoordinates(int x, int y) {
        var point = new Point2D(x, y);
        var snake = new Snake(point);
        var head = snake.getHead();

        assertEquals(
            point, head,
            "The snake should have exactly at specified coordinates"
        );
        assertEquals(
            1, snake.getSegments().size(),
            "The new snake should have exactly one segment"
        );
    }

    @Test
    void constructor_should_throwException_when_headIsNull() {
        assertThrows(NullPointerException.class, () -> new Snake(null));
    }

    @ParameterizedTest
    @MethodSource("generateCoordinatesSequence")
    void getLength_should_returnNumberOfSegments(List<Point2D> points) {
        var snake = new Snake();

        for (var point : points) {
            snake.putHead(point);

            var length = snake.getLength();
            var segments = snake.getSegments();

            assertEquals(
                segments.size(), length,
                "The length should be equal to the segments number"
            );
        }
    }

    @ParameterizedTest
    @MethodSource("generateCoordinates")
    void putHead_should_putHeadAtSpecifiedCoordinates(int x, int y) {
        var point = new Point2D(x, y);
        var snake = new Snake();

        snake.putHead(point);
        var head = snake.getHead();

        assertEquals(
            point, head,
            "The snake should have exactly at specified coordinates"
        );
    }

    @ParameterizedTest
    @MethodSource("generateCoordinatesSequence")
    void putHead_should_increaseBodyLength(List<Point2D> points) {
        var snake = new Snake();

        for (var point : points) {
            var oldLength = snake.getLength();

            snake.putHead(point);
            var newLength = snake.getLength();

            assertEquals(
                oldLength + 1, newLength,
                "The snake should increase its length exactly at 1 segment"
            );
        }
    }

    @Test
    void putHead_should_throwException_whenHeadIsNull() {
        var snake = new Snake();
        assertThrows(NullPointerException.class, () -> snake.putHead(null));
    }

    @ParameterizedTest
    @MethodSource("generateCoordinatesSequence")
    void popTail_should_popOneOfSegments(List<Point2D> points) {
        var snake = new Snake();

        for (var point : points) {
            snake.putHead(point);
        }

        while (snake.getLength() > MIN_TESTING_LENGTH) {
            var oldSegments = snake.getSegments();
            var tail = assertDoesNotThrow(
                snake::popTail,
                "Popping tail from the snake with more than one segment"
                    + " should not cause any exception"
            );
            var newSegments = snake.getSegments();

            assertTrue(
                oldSegments.contains(tail),
                "The removed tail should be one of the oldSegments"
            );
            assertFalse(
                newSegments.contains(tail),
                "The removed tail should not appear int the newSegments"
            );
        }
    }

    @ParameterizedTest
    @MethodSource("generateCoordinatesSequence")
    void popTail_should_decreaseBodyLength(List<Point2D> points) {
        var snake = new Snake();

        for (var point : points) {
            snake.putHead(point);
        }

        while (snake.getLength() > MIN_TESTING_LENGTH) {
            var oldLength = snake.getLength();
            assertDoesNotThrow(
                snake::popTail,
                "Popping tail from the snake with more than one segment"
                    + " should not cause any exception"
            );
            var newLength = snake.getLength();

            assertEquals(
                oldLength - 1, newLength,
                "The snake should decrease its length exactly at 1 segment"
            );
        }
    }

    @Test
    void popTail_should_throwException_when_thereIsOnlyOneSegment() {
        var snake = new Snake();

        assertThrows(
            SnakeZeroLengthException.class,
            snake::popTail,
            "Popping tail from the snake with only one segment should cause an exception"
        );
    }

    @Test
    void move_should_increaseBodyLength_when_growIsTrue() {
        var snake = new Snake();
        var point = new Point2D(0, 1);
        var oldLength = snake.getLength();

        snake.move(point, true);
        var newLength = snake.getLength();

        assertEquals(
            oldLength + 1, newLength,
            "The snake should increase its length exactly at 1 segment"
        );
    }

    @Test
    void move_shouldNot_changeBodyLength_when_growIsFalse() {
        var snake = new Snake();
        var point = new Point2D(0, 1);
        var oldLength = snake.getLength();

        snake.move(point, false);
        var newLength = snake.getLength();

        assertEquals(
            oldLength, newLength,
            "The snake should not change its length"
        );
    }

    @ParameterizedTest
    @MethodSource("generateCoordinates")
    void move_should_putHeadAtSpecifiedCoordinates(int x, int y) {
        var point = new Point2D(x, y);
        var snake = new Snake();

        snake.move(point);
        var head = snake.getHead();

        assertEquals(
            point, head,
            "The snake should have exactly at specified coordinates"
        );
    }

    @Test
    void move_should_throwException_when_headIsNull() {
        var snake = new Snake();
        assertThrows(NullPointerException.class, () -> snake.move(null));
    }

    static Stream<Arguments> generateCoordinates() {
        var values = List.of(0, 1_000, -21, Integer.MAX_VALUE, Integer.MIN_VALUE);
        var arguments = new ArrayList<Arguments>();

        for (var x : values) {
            for (var y : values) {
                arguments.add(Arguments.of(x, y));
            }
        }

        return arguments.stream();
    }

    static Stream<Arguments> generateCoordinatesSequence() {
        var arguments = new ArrayList<Arguments>();

        for (var length = 1; length < MAX_TESTING_LENGTH; length += TESTING_LENGTH_STEP) {
            var points = new ArrayList<Point2D>();
            int x = length / 2;
            int y = length / 3;

            for (var i = 0; i < length; i++) {
                if (x - y > i) {
                    y++;
                } else {
                    x++;
                }

                points.add(new Point2D(x, y));
            }
            arguments.add(Arguments.of(points));
        }

        return arguments.stream();
    }
}