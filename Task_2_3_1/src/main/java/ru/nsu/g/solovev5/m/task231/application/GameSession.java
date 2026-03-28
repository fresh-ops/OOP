package ru.nsu.g.solovev5.m.task231.application;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import ru.nsu.g.solovev5.m.task231.application.transport.GameStateRecord;
import ru.nsu.g.solovev5.m.task231.application.transport.SnakeRecord;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;

/**
 * A game coordinator object.
 */
public class GameSession {
    private final int rows;
    private final int columns;
    private final Snake snake;
    private final AtomicReference<GameStateRecord> state;

    /**
     * Creates a new game session with specified parameters.
     *
     * @param config the new game configuration
     */
    public GameSession(GameSessionConfig config) {
        rows = config.rows();
        columns = config.columns();
        snake = new Snake();
        state = new AtomicReference<>();
        freezeState();
    }

    /**
     * Performs a logic loop iteration.
     */
    public void tick() {
    }

    /**
     * Returns a frozen game state.
     *
     * @return a game state
     */
    public GameStateRecord getState() {
        return state.get();
    }

    /**
     * Freezes and saves current game state.
     */
    private void freezeState() {
        state.set(
            new GameStateRecord(
                rows, columns,
                List.of(
                    new SnakeRecord(snake.getSegments())
                )
            )
        );
    }
}
