package ru.nsu.g.solovev5.m.task231.presentation.board;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

class RectangleTest {
    @Test
    void constructor_shouldNot_fail() {
        assertDoesNotThrow(() -> new Rectangle(
                new RectangleShape(0, 0, 100, 100),
                Color.GREEN
            )
        );
    }
}