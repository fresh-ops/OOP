package ru.nsu.g.solovev5.m.task231.application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import ru.nsu.g.solovev5.m.task231.application.config.GameSessionConfig;
import ru.nsu.g.solovev5.m.task231.application.transport.GameStateRecord;
import ru.nsu.g.solovev5.m.task231.application.transport.SnakeRecord;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;

/**
 * A game coordinator object.
 */
public class GameSession {
    private final int rows;
    private final int columns;
    private final List<Player> players;
    private final AtomicReference<GameStateRecord> state;
    private final MoveSnakeUseCase moveSnakeUseCase;

    /**
     * Creates a new game session with specified parameters.
     *
     * @param config the new game configuration
     */
    public GameSession(GameSessionConfig config) {
        rows = config.rows();
        columns = config.columns();
        players = new ArrayList<>();
        for (var player : config.players()) {
            players.add(new Player(
                new Snake(player.snakeHead()),
                player.strategy()
            ));
        }
        state = new AtomicReference<>();
        moveSnakeUseCase = new MoveSnakeUseCase();
        freezeState();
    }

    /**
     * Performs a logic loop iteration.
     */
    public void tick() {
        for (var player : players) {
            moveSnakeUseCase.invoke(player.snake(), player.strategy(), false);
        }

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
        var snakes = new ArrayList<SnakeRecord>();
        for (var player : players) {
            snakes.add(new SnakeRecord(player.snake().getSegments()));
        }

        state.set(
            new GameStateRecord(rows, columns, snakes)
        );
    }
}
