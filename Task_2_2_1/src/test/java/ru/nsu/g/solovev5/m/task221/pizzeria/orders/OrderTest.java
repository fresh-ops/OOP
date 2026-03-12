package ru.nsu.g.solovev5.m.task221.pizzeria.orders;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.g.solovev5.m.task221.pizzeria.pizza.PizzaInMenu;

class OrderTest {
    @ParameterizedTest
    @MethodSource("validConstructorParameters")
    void initialState_should_followPassedParameters(PizzaInMenu menuPosition, int id) {
        var order = assertDoesNotThrow(() -> new Order(menuPosition, id));

        assertEquals(menuPosition, order.getPizzaType());
        assertEquals(id, order.getId());
        assertEquals(OrderStatus.NEW, order.getStatus());
        assertNotNull(order.getPizza());
    }

    static Stream<Arguments> validConstructorParameters() {
        return Stream.of(
            Arguments.of(PizzaInMenu.PEPPERONI, 123),
            Arguments.of(PizzaInMenu.MARGHERITA, 0),
            Arguments.of(PizzaInMenu.PEPPERONI, Integer.MAX_VALUE)
        );
    }

    @Test
    void promoteStatus_should_changeStatus_when_orderIsNotDone() {
        var order = new Order(PizzaInMenu.PEPPERONI, 0);

        while (order.getStatus() != OrderStatus.DONE) {
            var previousStatus = order.getStatus();
            order.promoteStatus();
            assertNotEquals(previousStatus, order.getStatus());
        }
    }
}