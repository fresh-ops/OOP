package ru.nsu.g.solovev5.m.task231.domain.entities;

import java.util.List;

/**
 * An object represents the whole game state.
 *
 * @param rows         the number of row on the board
 * @param columns      the number of columns on the board
 * @param maxFoodItems the maximum number of food items on the board
 * @param food         the list of food on the board
 * @param players      the list of alive players
 */
public record GameState(
    int rows,
    int columns,
    int maxFoodItems,
    List<Food> food,
    List<Player> players
) {
}
