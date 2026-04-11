package ru.nsu.g.solovev5.m.task231.application;

import java.util.List;
import ru.nsu.g.solovev5.m.task231.domain.entities.Food;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;

/**
 * Handles food eating.
 */
public class EatFoodUseCase {
    private final GetCollidingFoodUseCase getCollidingFoodUseCase;

    /**
     * Creates a new EatFoodUseCase.
     *
     * @param getCollidingFoodUseCase the use case for getting colliding food
     */
    public EatFoodUseCase(GetCollidingFoodUseCase getCollidingFoodUseCase) {
        this.getCollidingFoodUseCase = getCollidingFoodUseCase;
    }

    /**
     * Handles the eating process.
     *
     * @param player the eating player
     * @param food   available food items
     * @return eaten food
     */
    public List<Food> invoke(Player player, List<Food> food) {
        var eatenFood = getCollidingFoodUseCase.invoke(player, food);
        player.snake().addGrowthTicks(eatenFood.size());

        return eatenFood;
    }
}
