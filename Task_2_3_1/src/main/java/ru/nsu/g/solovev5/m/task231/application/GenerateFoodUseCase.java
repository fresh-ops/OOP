package ru.nsu.g.solovev5.m.task231.application;

import java.util.List;
import ru.nsu.g.solovev5.m.task231.application.strategies.CellPickingStrategy;
import ru.nsu.g.solovev5.m.task231.application.strategies.FoodTypePickingStrategy;
import ru.nsu.g.solovev5.m.task231.domain.entities.Food;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;

/**
 * Generates food items.
 */
public class GenerateFoodUseCase {
    private final GetFreeCellsUseCase getFreeCellsUseCase;
    private final CellPickingStrategy cellPickingStrategy;
    private final FoodTypePickingStrategy foodTypePickingStrategy;

    /**
     * Creates a new GenerateFoodUseCase.
     *
     * @param getFreeCellsUseCase     the GetFreeCellsUseCase
     * @param cellPickingStrategy     a strategy for picking a free cell
     * @param foodTypePickingStrategy a strategy for picking a food type
     */
    public GenerateFoodUseCase(
        GetFreeCellsUseCase getFreeCellsUseCase,
        CellPickingStrategy cellPickingStrategy,
        FoodTypePickingStrategy foodTypePickingStrategy
    ) {
        this.getFreeCellsUseCase = getFreeCellsUseCase;
        this.cellPickingStrategy = cellPickingStrategy;
        this.foodTypePickingStrategy = foodTypePickingStrategy;
    }

    /**
     * Generates a new food item.
     *
     * @param rows    the rows number on the board
     * @param columns the columns number on the board
     * @param players the players in this session
     * @param foods   the food items on the board
     * @return a generated food item
     */
    public Food invoke(int rows, int columns, List<Player> players, List<Food> foods) {
        var freeCells = getFreeCellsUseCase.invoke(rows, columns, players, foods);

        var position = cellPickingStrategy.pick(freeCells);
        var type = foodTypePickingStrategy.pick();

        return new Food(position, type);
    }
}
