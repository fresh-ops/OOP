package ru.nsu.g.solovev5.m.task221.pizzeria.warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.Order;
import ru.nsu.g.solovev5.m.task221.pizzeria.pizza.PizzaInMenu;

class PizzaWarehouseTest {
    @Test
    void takeAtMost_should_returnNoMoreThanRequired() throws InterruptedException {
        var warehouse = new PizzaWarehouse(15);

        for (var i = 0; i < 10; i++) {
            warehouse.put(new Order(PizzaInMenu.PEPPERONI, i));
        }

        var taken = warehouse.takeAtMost(5);
        assertTrue(taken.size() <= 5);
        assertFalse(taken.isEmpty());
    }

    @Test
    void takeAtMost_should_fail_when_amountIsZeroOrNegative() {
        var warehouse = new PizzaWarehouse(15);

        assertThrows(
            IllegalArgumentException.class,
            () -> warehouse.takeAtMost(0)
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> warehouse.takeAtMost(-10)
        );
    }

    @Test
    void collect_should_emptyTheWarehouse() throws InterruptedException {
        var warehouse = new PizzaWarehouse(15);
        var orders = List.of(
            new Order(PizzaInMenu.PEPPERONI, 0),
            new Order(PizzaInMenu.MARGHERITA, 1),
            new Order(PizzaInMenu.PEPPERONI, 2),
            new Order(PizzaInMenu.PEPPERONI, 3),
            new Order(PizzaInMenu.MARGHERITA, 4)
        );

        for (var order : orders) {
            warehouse.put(order);
        }

        var collected = warehouse.collect();
        assertEquals(orders.size(), collected.size());
        for (var order : orders) {
            assertTrue(collected.contains(order));
        }
    }
}