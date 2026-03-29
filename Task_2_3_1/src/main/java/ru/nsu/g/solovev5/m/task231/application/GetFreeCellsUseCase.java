package ru.nsu.g.solovev5.m.task231.application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ru.nsu.g.solovev5.m.task231.domain.entities.Food;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.services.CollisionService;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

/**
 * Get free cells.
 */
public class GetFreeCellsUseCase {
    /**
     * Finds free cells.
     *
     * @param rows    the rows number on the board
     * @param columns the columns number on the board
     * @param players the players in this session
     * @param foods   the food items on the board
     * @return free cells on the board
     */
    public List<Point2D> invoke(int rows, int columns, List<Player> players, List<Food> foods) {
        var freeCells = new ArrayList<Point2D>();
        var occupiedCells = getOccupiedCells(players, foods);

        for (var x = 0; x < rows; x++) {
            for (var y = 0; y < columns; y++) {
                var candidate = new Point2D(x, y);
                if (!CollisionService.collides(occupiedCells, candidate)) {
                    freeCells.add(new Point2D(x, y));
                }
            }
        }

        return freeCells;
    }

    /**
     * Concatenates all occupied points in a single set.
     *
     * @param players the players in this session
     * @param foods   the food items on the board
     * @return a set of occupied cells
     */
    private Set<Point2D> getOccupiedCells(List<Player> players, List<Food> foods) {
        var occupiedCells = new HashSet<Point2D>();

        for (var player : players) {
            occupiedCells.addAll(player.snake().getSegments());
        }

        for (var food : foods) {
            occupiedCells.add(food.position());
        }

        return occupiedCells;
    }
}
