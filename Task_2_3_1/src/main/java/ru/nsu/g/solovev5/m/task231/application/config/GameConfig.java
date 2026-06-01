package ru.nsu.g.solovev5.m.task231.application.config;

import java.util.List;

/**
 * The basic game configuration.
 *
 * @param rows         number of rows on the game board
 * @param columns      number of columns on the game board
 * @param maxFoodItems maximum number of food items on the board
 * @param players      the players' configurations
 */
public record GameConfig(
    int rows,
    int columns,
    int maxFoodItems,
    List<PlayerConfig> players
) {
}
