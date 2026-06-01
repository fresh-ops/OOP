package ru.nsu.g.solovev5.m.task231.domain.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.g.solovev5.m.task231.domain.entities.Food;
import ru.nsu.g.solovev5.m.task231.domain.entities.Player;
import ru.nsu.g.solovev5.m.task231.domain.entities.Snake;
import ru.nsu.g.solovev5.m.task231.domain.valueobjects.Point2D;

class EatFoodServiceTest {
    EatFoodService eatFoodUseCase;

    @BeforeEach
    void setUp() {
        eatFoodUseCase = new EatFoodService();
    }

    @ParameterizedTest
    @MethodSource("impossibleToEat")
    void eat_should_returnEmptyList_when_noFoodWasEaten(Player player, List<Food> food) {
        var eaten = eatFoodUseCase.eat(player, food);

        assertTrue(eaten.isEmpty());
    }

    static Stream<Arguments> impossibleToEat() {
        var player = new Player(
            new Snake(new Point2D(0, 0)),
            (head) -> head
        );

        return Stream.of(
            Arguments.of(player, List.of(
                new Food(new Point2D(0, 1)),
                new Food(new Point2D(1, 0)),
                new Food(new Point2D(1, 1))
            )),
            Arguments.of(player, List.of())
        );
    }

}