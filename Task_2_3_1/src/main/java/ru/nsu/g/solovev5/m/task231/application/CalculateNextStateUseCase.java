package ru.nsu.g.solovev5.m.task231.application;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.g.solovev5.m.task231.application.exceptions.NoFreeCellsException;
import ru.nsu.g.solovev5.m.task231.domain.entities.Food;
import ru.nsu.g.solovev5.m.task231.domain.entities.GameState;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;

/**
 * Calculates the next game state.
 */
public class CalculateNextStateUseCase {
    private final GenerateFoodUseCase generateFoodUseCase;
    private final MoveSnakeUseCase moveSnakeUseCase;
    private final CheckDeadCollisionsUseCase checkDeadCollisionsUseCase;
    private final EatFoodUseCase eatFoodUseCase;

    /**
     * Creates a new CalculateNextStateUseCase.
     *
     * @param generateFoodUseCase    the use case to generate food
     * @param moveSnakeUseCase       the use case to move snake
     * @param checkDeadCollisionsUseCase the use case to check collisions
     * @param eatFoodUseCase         the use case to eat food
     */
    public CalculateNextStateUseCase(
        GenerateFoodUseCase generateFoodUseCase,
        MoveSnakeUseCase moveSnakeUseCase,
        CheckDeadCollisionsUseCase checkDeadCollisionsUseCase,
        EatFoodUseCase eatFoodUseCase
    ) {
        this.generateFoodUseCase = generateFoodUseCase;
        this.moveSnakeUseCase = moveSnakeUseCase;
        this.checkDeadCollisionsUseCase = checkDeadCollisionsUseCase;
        this.eatFoodUseCase = eatFoodUseCase;
    }

    /**
     * Calculates a new game state based on given.
     *
     * @param state the previous game state
     * @return a new game state
     */
    public GameState invoke(GameState state) {
        var food = generateFood(state);
        var alivePlayers = movePlayersAndHandleCollisions(
            state.rows(), state.columns(), state.players(), food
        );

        return new GameState(
            state.rows(), state.columns(),
            state.maxFoodItems(),
            List.copyOf(food),
            List.copyOf(alivePlayers)
        );
    }

    /**
     * Generates new food items until the limit exceed or no free cell found.
     *
     * @param state the game state
     * @return a new list of food items
     */
    private List<Food> generateFood(GameState state) {
        var newFood = new ArrayList<>(state.food());

        try {
            while (newFood.size() < state.maxFoodItems()) {
                newFood.add(generateFoodUseCase.invoke(
                    state.rows(), state.columns(), state.players(), newFood)
                );
            }
        } catch (NoFreeCellsException ignored) {
            return newFood;
        }

        return newFood;
    }

    /**
     * Moves players and handles collisions.
     *
     * @param rows    the number of rows on the board
     * @param columns the number of columns on the board
     * @param players alive players
     * @param food    food items on the board
     * @return a list of alive players
     */
    private List<Player> movePlayersAndHandleCollisions(
        int rows, int columns, List<Player> players, List<Food> food
    ) {
        var alivePlayers = new ArrayList<Player>();

        for (var player : players) {
            moveSnakeUseCase.invoke(player.snake(), player.strategy());
            var eatenFood = eatFoodUseCase.invoke(player, food);
            food.removeAll(eatenFood);

            if (!checkDeadCollisionsUseCase.invoke(player, rows, columns, players)) {
                alivePlayers.add(player);
            }
        }

        return alivePlayers;
    }
}
