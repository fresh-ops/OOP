package ru.nsu.g.solovev5.m.task231.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.g.solovev5.m.task231.application.strategies.NormalFoodTypePickingStrategy;
import ru.nsu.g.solovev5.m.task231.application.strategies.cellpicking.FirstFreeCellPickingStrategy;
import ru.nsu.g.solovev5.m.task231.domain.entities.Food;
import ru.nsu.g.solovev5.m.task231.domain.entities.GameState;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

class CalculateNextStateUseCaseTest {
    CalculateNextStateUseCase calculateNextStateUseCase;

    @BeforeEach
    void setUp() {
        calculateNextStateUseCase = new CalculateNextStateUseCase(
            new GenerateFoodUseCase(
                new GetFreeCellsUseCase(),
                new FirstFreeCellPickingStrategy(),
                new NormalFoodTypePickingStrategy()
            ),
            new MoveSnakeUseCase(),
            new CheckDeadCollisionsUseCase(
                new CheckBorderCollisionsUseCase(),
                new CheckSelfCollisionsUseCase(),
                new CheckEnemiesCollisions()
            ),
            new EatFoodUseCase(
                new GetCollidingFoodUseCase()
            )
        );
    }

    @ParameterizedTest
    @MethodSource("validFood")
    void invoke_shouldNot_generateFoodOverLimit(int maxFoodItems, List<Food> food) {
        var state = new GameState(
            2, 2,
            maxFoodItems,
            food,
            List.of()
        );

        var newState = calculateNextStateUseCase.invoke(state);

        assertTrue(newState.food().size() <= newState.maxFoodItems());
    }

    @Test
    void invoke_shouldNot_changeConfiguration() {
        var state = new GameState(
            12, 15,
            0,
            List.of(),
            List.of()
        );

        var newState = calculateNextStateUseCase.invoke(state);

        assertEquals(state.rows(), newState.rows());
        assertEquals(state.columns(), newState.columns());
        assertEquals(state.maxFoodItems(), newState.maxFoodItems());
    }

    @Test
    void invoke_should_killPlayers_when_theyHaveDeadCollision() {
        var state = new GameState(
            12, 15,
            0,
            List.of(),
            List.of(
                new Player(
                    new Snake(new Point2D(100, 100)),
                    (head) -> head
                )
            )
        );

        var newState = calculateNextStateUseCase.invoke(state);

        assertTrue(newState.players().isEmpty());
    }

    @Test
    void invoke_should_removeEatenFood() {
        var state = new GameState(
            12, 15,
            1,
            List.of(
                new Food(new Point2D(3, 3))
            ),
            List.of(
                new Player(
                    new Snake(new Point2D(2, 3)),
                    (head) -> new Point2D(head.x() + 1, head.y())
                )
            )
        );

        var newState = calculateNextStateUseCase.invoke(state);

        assertTrue(newState.food().isEmpty());
    }

    static Stream<Arguments> validFood() {
        return Stream.of(
            Arguments.of(1, List.of()),
            Arguments.of(100, List.of()),
            Arguments.of(0, List.of())
        );
    }
}