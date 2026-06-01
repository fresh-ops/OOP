package ru.nsu.g.solovev5.m.task221.pizzeria.orders;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.g.solovev5.m.task221.pizzeria.pizza.PizzaInMenu;

class OrderQueueTest {
    @ParameterizedTest
    @MethodSource("validOrders")
    void take_should_keepOrder(List<Order> orders) {
        var queue = new OrderQueue();
        for (var order : orders) {
            assertDoesNotThrow(() -> queue.put(order));
        }

        for (var order : orders) {
            var takenOrder = assertDoesNotThrow(queue::take);
            assertEquals(order, takenOrder);
        }
    }

    @ParameterizedTest
    @MethodSource("validOrders")
    void isEmpty_should_returnFalse_whenThereIsElements(List<Order> orders) {
        var queue = new OrderQueue();

        for (var order : orders) {
            assertDoesNotThrow(() -> queue.put(order));
        }

        assertFalse(queue.isEmpty());
    }

    static Stream<Arguments> validOrders() {
        return Stream.of(
            Arguments.of(List.of(
                new Order(PizzaInMenu.MARGHERITA, 0),
                new Order(PizzaInMenu.PEPPERONI, 1),
                new Order(PizzaInMenu.MARGHERITA, 2)
            ))
        );
    }

    @Test
    void isEmpty_should_returnTrue_whenThereIsNoOrder() {
        var queue = new OrderQueue();
        assertTrue(queue.isEmpty());
        assertDoesNotThrow(() -> {
            queue.put(new Order(PizzaInMenu.MARGHERITA, 0));
            queue.take();
        });
        assertTrue(queue.isEmpty());
    }

    @Test
    void collect_should_emptyTheQueue() {
        var queue = new OrderQueue();
        var orders = List.of(
            new Order(PizzaInMenu.PEPPERONI, 0),
            new Order(PizzaInMenu.MARGHERITA, 1),
            new Order(PizzaInMenu.PEPPERONI, 2),
            new Order(PizzaInMenu.PEPPERONI, 3),
            new Order(PizzaInMenu.MARGHERITA, 4)
        );

        for (var order : orders) {
            queue.put(order);
        }

        var collected = queue.collect();
        assertEquals(orders.size(), collected.size());
        for (var order : orders) {
            assertTrue(collected.contains(order));
        }
        assertTrue(queue.isEmpty());
    }
}