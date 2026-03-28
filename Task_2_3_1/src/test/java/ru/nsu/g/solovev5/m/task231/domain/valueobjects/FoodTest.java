package ru.nsu.g.solovev5.m.task231.domain.valueobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task231.domain.entities.Food;

class FoodTest {
    @Test
    void constructor_shouldNot_acceptNullPosition() {
        assertThrows(NullPointerException.class, () -> new Food(null));
    }

    @Test
    void constructor_shouldNot_acceptNullType() {
        assertThrows(NullPointerException.class, () -> new Food(new Point2D(0, 0), null));
    }

    @Test
    void constructor_should_setFoodTypeToNormal_when_usingShortNotation() {
        var food = new Food(new Point2D(0, 0));
        assertEquals(FoodType.NORMAL, food.type());
    }
}