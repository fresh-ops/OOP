package ru.nsu.g.solovev5.m.task231.application.config;

import java.util.List;
import ru.nsu.g.solovev5.m.task231.application.strategies.CellPickingStrategy;
import ru.nsu.g.solovev5.m.task231.application.strategies.FoodTypePickingStrategy;

/**
 * The basic game configuration.
 *
 * @param rows                    number of rows on the game board
 * @param columns                 number of columns on the game board
 * @param foodsNumber             maximum number of food items on the board
 * @param players                 the players' configurations
 * @param cellPickingStrategy     the strategy for picking a cell
 * @param foodTypePickingStrategy the strategy for picking a food type
 */
public record GameSessionConfig(
    int rows,
    int columns,
    int foodsNumber,
    List<PlayerConfig> players,
    CellPickingStrategy cellPickingStrategy,
    FoodTypePickingStrategy foodTypePickingStrategy
) {
}
