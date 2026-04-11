package ru.nsu.g.solovev5.m.task231.domain.services;

import java.util.List;
import ru.nsu.g.solovev5.m.task231.domain.entities.Food;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;

/**
 * Handles food eating.
 */
public class EatFoodService {
    /**
     * Handles the eating process.
     *
     * @param player the eating player
     * @param food   available food items
     * @return eaten food
     */
    public List<Food> eat(Player player, List<Food> food) {
        var eatenFood = getCollidingFood(player, food);
        player.snake().addGrowthTicks(eatenFood.size());

        return eatenFood;
    }

    /**
     * Looks for collisions with food items.
     *
     * @param player the player to check
     * @param foods  food items
     * @return food items that player collides with
     */
    private List<Food> getCollidingFood(Player player, List<Food> foods) {
        return foods.stream()
            .filter(f -> CollisionService.collides(player.snake().getHead(), f.position()))
            .toList();
    }
}
