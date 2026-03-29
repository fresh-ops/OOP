package ru.nsu.g.solovev5.m.task231.application.transport;

import java.util.List;

/**
 * A transferable copy of the game state.
 *
 * @param rows    the number of rows on the game board
 * @param columns the number of columns on the game board
 * @param snakes  transferable copies of players' snakes
 * @param foods   transferable copies of food items
 */
public record GameStateRecord(
    int rows,
    int columns,
    List<SnakeRecord> snakes,
    List<FoodRecord> foods
) {
}
