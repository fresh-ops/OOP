package ru.nsu.g.solovev5.m.task231.application;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.g.solovev5.m.task231.application.config.GameConfig;
import ru.nsu.g.solovev5.m.task231.domain.entities.GameState;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;

/**
 * Creates a game state from configuration object.
 */
public class CreateGameStateFromConfigUseCase {
    /**
     * Creates a new game state from config.
     *
     * @param config the game configuration
     * @return a new game state
     */
    public GameState invoke(GameConfig config) {
        var players = new ArrayList<Player>();

        for (var playerConfig : config.players()) {
            players.add(
                new Player(
                    new Snake(playerConfig.snakeHead()),
                    playerConfig.strategy()
                )
            );
        }

        return new GameState(
            config.rows(),
            config.columns(),
            config.maxFoodItems(),
            List.of(),
            List.copyOf(players)
        );
    }
}
