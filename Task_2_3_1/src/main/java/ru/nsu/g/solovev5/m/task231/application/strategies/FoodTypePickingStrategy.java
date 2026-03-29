package ru.nsu.g.solovev5.m.task231.application.strategies;

import ru.nsu.g.solovev5.m.task231.domain.valueobjects.FoodType;

/**
 * A strategy for picking a food type.
 */
public interface FoodTypePickingStrategy {
    /**
     * Picks a food type.
     *
     * @return a picked food type
     */
    FoodType pick();
}
