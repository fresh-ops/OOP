package ru.nsu.g.solovev5.m.task231.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task231.application.config.GameConfig;
import ru.nsu.g.solovev5.m.task231.application.config.PlayerConfig;
import ru.nsu.g.solovev5.m.task231.domain.strategies.MovementStrategy;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

class StartNewGameSessionUseCaseTest {
    StartNewGameSessionUseCase startNewGameSessionUseCase;

    @BeforeEach
    void setUp() {
        startNewGameSessionUseCase = new StartNewGameSessionUseCase();
    }

    @Test
    void invoke_should_putAllFieldsUnchanged() {
        MovementStrategy strategy = (head) -> head;
        var config = new GameConfig(
            12, 15,
            3,
            List.of(
                new PlayerConfig(
                    new Point2D(0, 2),
                    strategy
                )
            )
        );

        var session = startNewGameSessionUseCase.invoke(config, null);
        var state = session.getGameState();

        assertEquals(config.rows(), state.rows());
        assertEquals(config.columns(), state.columns());
        assertEquals(config.maxFoodItems(), state.maxFoodItems());
        assertEquals(config.players().size(), state.players().size());

        for (var i = 0; i < config.players().size(); i++) {
            assertEquals(
                config.players().get(i).snakeHead(),
                state.players().get(i).snake().getHead()
            );
            assertEquals(
                config.players().get(i).strategy(),
                state.players().get(i).strategy()
            );
        }
    }
}