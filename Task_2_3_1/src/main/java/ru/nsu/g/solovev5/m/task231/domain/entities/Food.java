package ru.nsu.g.solovev5.m.task231.domain.entities;

import java.util.Objects;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.FoodType;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

/**
 * A food item in the game.
 *
 * @param position the position of this food item
 * @param type     the type of this food item
 */
public record Food(Point2D position, FoodType type) {

    /**
     * Creates a new Food.
     *
     * @param position the position of this food item
     * @param type     the type of this food item
     */
    public Food {
        Objects.requireNonNull(position, "position must not be null");
        Objects.requireNonNull(type, "type must not be null");
    }

    /**
     * Creates new normal Food.
     *
     * @param position the position of this food item
     */
    public Food(Point2D position) {
        this(position, FoodType.NORMAL);
    }
}
