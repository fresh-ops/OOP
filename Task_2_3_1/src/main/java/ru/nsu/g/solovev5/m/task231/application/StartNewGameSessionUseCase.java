package ru.nsu.g.solovev5.m.task231.application;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.g.solovev5.m.task231.application.config.GameConfig;
import ru.nsu.g.solovev5.m.task231.domain.entities.GameState;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;
import ru.nsu.g.solovev5.m.task231.domain.services.GameTickService;

/**
 * Creates a game session from configuration object.
 */
public class StartNewGameSessionUseCase {
    /**
     * Creates a new game session from config.
     *
     * @param config          the game configuration
     * @param gameTickService the service to calculate new game state
     * @return a new game session
     */
    public GameSession invoke(GameConfig config, GameTickService gameTickService) {
        var players = new ArrayList<Player>();

        for (var playerConfig : config.players()) {
            players.add(
                new Player(
                    new Snake(playerConfig.snakeHead()),
                    playerConfig.strategy()
                )
            );
        }

        var initialState = new GameState(
            config.rows(),
            config.columns(),
            config.maxFoodItems(),
            List.of(),
            List.copyOf(players)
        );

        return new GameSession(gameTickService, initialState);
    }
}
