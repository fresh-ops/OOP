package ru.nsu.g.solovev5.m.task231.application;

import java.util.concurrent.atomic.AtomicReference;
import ru.nsu.g.solovev5.m.task231.application.config.GameConfig;
import ru.nsu.g.solovev5.m.task231.application.dto.FoodDto;
import ru.nsu.g.solovev5.m.task231.application.dto.GameStateDto;
import ru.nsu.g.solovev5.m.task231.application.dto.SnakeDto;
import ru.nsu.g.solovev5.m.task231.domain.entities.GameState;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;

/**
 * A worker that runs game loop.
 */
public class GameWorker implements Runnable {
    private final CalculateNextStateUseCase calculateNextStateUseCase;
    private final AtomicReference<GameState> gameState;

    /**
     * Creates a new GameWorker.
     *
     * @param createGameStateFromConfigUseCase the use case to get initial state
     * @param calculateNextStateUseCase        the use case to change state
     * @param config                           the game configuration
     */
    public GameWorker(
        CreateGameStateFromConfigUseCase createGameStateFromConfigUseCase,
        CalculateNextStateUseCase calculateNextStateUseCase,
        GameConfig config
    ) {
        this.calculateNextStateUseCase = calculateNextStateUseCase;

        var state = createGameStateFromConfigUseCase.invoke(config);
        this.gameState = new AtomicReference<>(state);
    }

    /**
     * Returns the game state.
     *
     * @return the game state
     */
    public GameState getGameState() {
        return gameState.get();
    }

    /**
     * Returns the recorded game state.
     *
     * @return the recorded game state
     */
    public GameStateDto getStateRecord() {
        var state = gameState.get();
        var food = state.food().stream()
            .map(f -> new FoodDto(f.position(), f.type()))
            .toList();
        var snakes = state.players().stream()
            .map(Player::snake)
            .map(s -> new SnakeDto(s.getHead(), s.getTail(), s.getBody()))
            .toList();
        return new GameStateDto(
            state.rows(),
            state.columns(),
            snakes,
            food
        );
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            var oldState = gameState.get();
            var newState = calculateNextStateUseCase.invoke(oldState);
            gameState.set(newState);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
