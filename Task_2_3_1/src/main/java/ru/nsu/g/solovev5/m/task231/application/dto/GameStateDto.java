package ru.nsu.g.solovev5.m.task231.application.dto;

import java.util.List;

/**
 * A transferable copy of the game state.
 *
 * @param rows    the number of rows on the game board
 * @param columns the number of columns on the game board
 * @param snakes  transferable copies of players' snakes
 * @param foods   transferable copies of food items
 */
public record GameStateDto(
    int rows,
    int columns,
    List<SnakeDto> snakes,
    List<FoodDto> foods
) {
}
