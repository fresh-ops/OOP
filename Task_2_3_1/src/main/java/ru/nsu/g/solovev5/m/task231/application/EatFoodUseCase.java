package ru.nsu.g.solovev5.m.task231.application;

import java.util.List;
import ru.nsu.g.solovev5.m.task231.domain.entities.Food;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;

/**
 * Handles food eating.
 */
public class EatFoodUseCase {
    private final CheckCollisionsUseCase checkCollisionsUseCase;

    /**
     * Creates a new EatFoodUseCase.
     *
     * @param checkCollisionsUseCase the use case for collision checks
     */
    public EatFoodUseCase(CheckCollisionsUseCase checkCollisionsUseCase) {
        this.checkCollisionsUseCase = checkCollisionsUseCase;
    }

    /**
     * Handles the eating process.
     *
     * @param player the eating player
     * @param food   available food items
     * @return eaten food
     */
    public List<Food> invoke(Player player, List<Food> food) {
        var eatenFood = checkCollisionsUseCase.getFoodCollisions(player, food);
        player.snake().addGrowthTicks(eatenFood.size());

        return eatenFood;
    }
}
