package ru.nsu.g.solovev5.m.task231.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;
import ru.nsu.g.solovev5.m.task231.domain.strategies.MovementStrategy;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

class MoveSnakeUseCaseTest {
    @ParameterizedTest
    @MethodSource("generateStrategies")
    void invoke_should_moveSnakeBaseOnTheStrategy(MovementStrategy strategy) {
        var snake = new Snake();
        var oldHead = snake.getHead();

        var useCase = new MoveSnakeUseCase();
        useCase.invoke(snake, strategy);

        var newHead = snake.getHead();

        assertEquals(strategy.nextHead(oldHead), newHead);
    }

    static Stream<Arguments> generateStrategies() {
        var strategies = new ArrayList<MovementStrategy>();

        for (var x = -1; x < 2; x++) {
            for (var y = -1; y < 2; y++) {
                int finalX = x;
                int finalY = y;
                strategies.add((head) -> new Point2D(head.x() + finalX, head.y() + finalY));
            }
        }

        return strategies.stream()
            .map(Arguments::of);
    }
}