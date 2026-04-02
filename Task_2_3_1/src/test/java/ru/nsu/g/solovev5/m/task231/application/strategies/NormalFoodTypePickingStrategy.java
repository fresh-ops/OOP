package ru.nsu.g.solovev5.m.task231.application.strategies;

import ru.nsu.g.solovev5.m.task231.domain.valueobjects.FoodType;

/**
 * Picks only the normal food type.
 */
public class NormalFoodTypePickingStrategy implements FoodTypePickingStrategy {
    @Override
    public FoodType pick() {
        return FoodType.NORMAL;
    }
}
