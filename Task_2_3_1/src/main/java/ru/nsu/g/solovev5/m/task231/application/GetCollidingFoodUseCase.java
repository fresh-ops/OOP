package ru.nsu.g.solovev5.m.task231.application;

import java.util.List;
import ru.nsu.g.solovev5.m.task231.domain.entities.Food;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.services.CollisionService;

/**
 * Gets food items player can eat.
 */
public class GetCollidingFoodUseCase {
    /**
     * Looks for collisions with food items.
     *
     * @param player the player to check
     * @param foods  food items
     * @return food items that player collides with
     */
    public List<Food> invoke(Player player, List<Food> foods) {
        return foods.stream()
            .filter(f -> CollisionService.collides(player.snake().getHead(), f.position()))
            .toList();
    }
}
