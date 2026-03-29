package ru.nsu.g.solovev5.m.task231.application.transport;

import ru.nsu.g.solovev5.m.task231.domain.valueobjects.FoodType;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

/**
 * A transferable copy of food.
 *
 * @param position the food position
 * @param foodType the food type
 */
public record FoodRecord(Point2D position, FoodType foodType) {
}
