package ru.nsu.g.solovev5.m.task231.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task231.application.dto.SnakeDto;
import ru.nsu.g.solovev5.m.task231.domain.entities.GameState;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;

class GameSessionTest {
    static final GameState INITIAL_STATE = new GameState(
        12, 15,
        5,
        List.of(),
        List.of(
            new Player(
                new Snake(),
                head -> head
            )
        )
    );

    GameSession worker;

    @BeforeEach
    void setUp() {
        worker = new GameSession(
            null,
            INITIAL_STATE
        );
    }

    @Test
    void getGameState_should_returnStateEqualToConfig_when_noTicksDone() {
        var state = worker.getGameState();

        assertEquals(
            INITIAL_STATE.rows(),
            state.rows()
        );
        assertEquals(
            INITIAL_STATE.columns(),
            state.columns()
        );
        assertEquals(
            INITIAL_STATE.maxFoodItems(),
            state.maxFoodItems()
        );
        assertEquals(
            INITIAL_STATE.players().stream()
                .map(Player::snake)
                .map(Snake::getHead)
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
            INITIAL_STATE.rows(),
            state.rows()
        );
        assertEquals(
            INITIAL_STATE.columns(),
            state.columns()
        );
        assertEquals(0, state.foods().size());
        assertEquals(
            INITIAL_STATE.players().stream()
                .map(Player::snake)
                .map(Snake::getHead)
                .toList(),
            state.snakes().stream()
                .map(SnakeDto::head)
                .toList()
        );
    }
}