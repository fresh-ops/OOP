package ru.nsu.g.solovev5.m.task221.pizzeria.reaper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.Order;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.OrderQueue;
import ru.nsu.g.solovev5.m.task221.pizzeria.pizza.PizzaInMenu;

class OrderReaperTest {
    @Test
    void getCollected_should_returnAllOrders() {
        var orderReaper = new OrderReaper();
        var reapable = new OrderQueue();
        var orders = List.of(
            new Order(PizzaInMenu.PEPPERONI, 0),
            new Order(PizzaInMenu.MARGHERITA, 1),
            new Order(PizzaInMenu.PEPPERONI, 2),
            new Order(PizzaInMenu.PEPPERONI, 3),
            new Order(PizzaInMenu.MARGHERITA, 4)
        );

        for (var order : orders) {
            reapable.put(order);
        }

        orderReaper.reap(reapable);
        var collected = orderReaper.getCollected();

        assertEquals(orders.size(), collected.size());
        for (var order : orders) {
            assertTrue(collected.contains(order));
        }
    }
}