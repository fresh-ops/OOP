package ru.nsu.g.solovev5.m.task231.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task231.application.config.GameConfig;
import ru.nsu.g.solovev5.m.task231.application.config.PlayerConfig;
import ru.nsu.g.solovev5.m.task231.application.dto.SnakeDto;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

class GameWorkerTest {
    static final GameConfig CONFIG = new GameConfig(
        12, 15,
        5,
        List.of(
            new PlayerConfig(
                new Point2D(0, 0),
                head -> head
            )
        )
    );

    GameWorker worker;

    @BeforeEach
    void setUp() {
        worker = new GameWorker(
            new CreateGameStateFromConfigUseCase(),
            null,
            CONFIG
        );
    }

    @Test
    void getGameState_should_returnStateEqualToConfig_when_noTicksDone() {
        var state = worker.getGameState();

        assertEquals(
            CONFIG.rows(),
            state.rows()
        );
        assertEquals(
            CONFIG.columns(),
            state.columns()
        );
        assertEquals(
            CONFIG.maxFoodItems(),
            state.maxFoodItems()
        );
        assertEquals(
            CONFIG.players().stream()
                .map(PlayerConfig::snakeHead)
                .toList(),
            state.players().stream()
                .map(Player::snake)
                .map(Snake::getHead)
                .toList()
        );
    }

    @Test
    void getStateRecord_should_returnRecordEqualToConfig_when_noTicksDone() {
        var state = worker.getStateRecord();

        assertEquals(
            CONFIG.rows(),
            state.rows()
        );
        assertEquals(
            CONFIG.columns(),
            state.columns()
        );
        assertEquals(0, state.foods().size());
        assertEquals(
            CONFIG.players().stream()
                .map(PlayerConfig::snakeHead)
                .toList(),
            state.snakes().stream()
                .map(SnakeDto::head)
                .toList()
        );
    }
}