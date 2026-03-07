package ru.nsu.g.solovev5.m.task221.pizzeria.warehouse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}