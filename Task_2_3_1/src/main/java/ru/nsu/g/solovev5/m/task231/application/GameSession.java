package ru.nsu.g.solovev5.m.task231.application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import ru.nsu.g.solovev5.m.task231.application.config.GameSessionConfig;
import ru.nsu.g.solovev5.m.task231.application.transport.FoodRecord;
import ru.nsu.g.solovev5.m.task231.application.transport.GameStateRecord;
import ru.nsu.g.solovev5.m.task231.application.transport.SnakeRecord;
import ru.nsu.g.solovev5.m.task231.domain.entities.Food;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;

/**
 * A game coordinator object.
 */
public class GameSession {
    private final int rows;
    private final int columns;
    private final int foodsNumber;
    private final List<Food> foods;
    private final List<Player> players;
    private final AtomicReference<GameStateRecord> state;

    private final MoveSnakeUseCase moveSnakeUseCase;
    private final GetFreeCellsUseCase getFreeCellsUseCase;
    private final GenerateFoodUseCase generateFoodUseCase;
    private final CheckCollisionsUseCase checkCollisionsUseCase;

    /**
     * Creates a new game session with specified parameters.
     *
     * @param config the new game configuration
     */
    public GameSession(GameSessionConfig config) {
        rows = config.rows();
        columns = config.columns();
        foodsNumber = config.foodsNumber();

        foods = new ArrayList<>();
        players = new ArrayList<>();
        for (var player : config.players()) {
            players.add(new Player(
                new Snake(player.snakeHead()),
                player.strategy()
            ));
        }
        state = new AtomicReference<>();
        freezeState();

        moveSnakeUseCase = new MoveSnakeUseCase();
        getFreeCellsUseCase = new GetFreeCellsUseCase();
        generateFoodUseCase = new GenerateFoodUseCase(
            getFreeCellsUseCase,
            config.cellPickingStrategy(),
            config.foodTypePickingStrategy()
        );
        checkCollisionsUseCase = new CheckCollisionsUseCase();
    }

    /**
     * Performs a logic loop iteration.
     */
    public void tick() {
        while (foods.size() < foodsNumber) {
            foods.add(generateFoodUseCase.invoke(rows, columns, players, foods));
        }
        var playersToKill = new ArrayList<Player>();
        for (var player : players) {
            moveSnakeUseCase.invoke(player.snake(), player.strategy());
            var eatenFood = checkCollisionsUseCase.getFoodCollisions(player, foods);
            if (!eatenFood.isEmpty()) {
                foods.removeAll(eatenFood);
                player.snake().addGrowthTicks(eatenFood.size());
            }
            if (checkCollisionsUseCase.isDeadCollision(player, rows, columns, players)) {
                playersToKill.add(player);
            }
        }
        players.removeAll(playersToKill);


        freezeState();
    }

    /**
     * Returns a frozen game state.
     *
     * @return a game state
     */
    public GameStateRecord getFrozenState() {
        return state.get();
    }

    /**
     * Freezes and saves current game state.
     */
    private void freezeState() {
        var snakes = this.players.stream()
            .map(p -> new SnakeRecord(p.snake().getSegments()))
            .toList();

        var foods = this.foods.stream()
            .map(f -> new FoodRecord(f.position(), f.type()))
            .toList();

        state.set(
            new GameStateRecord(rows, columns, snakes, foods)
        );
    }
}
