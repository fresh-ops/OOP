package ru.nsu.g.solovev5.m.task231.adapters.keyboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MoveDirectionTest {
    @ParameterizedTest
    @MethodSource("getOppositePairs")
    void opposite_should_returnCorrectDirection(MoveDirection direction, MoveDirection opposite) {
        assertEquals(opposite, direction.opposite());
    }

    static Stream<Arguments> getOppositePairs() {
        return Stream.of(
            Arguments.of(MoveDirection.UP, MoveDirection.DOWN),
            Arguments.of(MoveDirection.LEFT, MoveDirection.RIGHT),
            Arguments.of(MoveDirection.RIGHT, MoveDirection.LEFT),
            Arguments.of(MoveDirection.DOWN, MoveDirection.UP)
        );
    }
}