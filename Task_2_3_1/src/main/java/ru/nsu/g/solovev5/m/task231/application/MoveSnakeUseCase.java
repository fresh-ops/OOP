package ru.nsu.g.solovev5.m.task231.application;

import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;
import ru.nsu.g.solovev5.m.task231.domain.strategies.MovementStrategy;

/**
 * Moves a snake.
 */
public class MoveSnakeUseCase {
    /**
     * Invokes this use case.
     *
     * @param snake    the snake to move
     * @param strategy the movement strategy
     */
    public void invoke(Snake snake, MovementStrategy strategy) {
        var currentHead = snake.getHead();
        var nextHead = strategy.nextHead(currentHead);
        snake.move(nextHead);
    }
}
