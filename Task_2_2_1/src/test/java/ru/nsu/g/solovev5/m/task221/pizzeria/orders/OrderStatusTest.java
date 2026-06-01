package ru.nsu.g.solovev5.m.task221.pizzeria.orders;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OrderStatusTest {

    @ParameterizedTest
    @MethodSource("notDoneStatuses")
    void next_should_notFail_when_statusIsNotDone(OrderStatus status) {
        assertDoesNotThrow(status::next);
    }

    static Stream<Arguments> notDoneStatuses() {
        var arguments = new ArrayList<Arguments>();

        for (var status : OrderStatus.values()) {
            if (status != OrderStatus.DONE) {
                arguments.add(Arguments.of(status));
            }
        }

        return arguments.stream();
    }

    @Test
    void next_should_fail_when_statusIsDone() {
        var status = OrderStatus.DONE;
        assertThrows(IllegalStateException.class, status::next);
    }
}