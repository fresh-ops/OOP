package ru.nsu.g.solovev5.m.task221.pizzeria.actors;

import ru.nsu.g.solovev5.m.task221.pizzeria.orders.Order;
import ru.nsu.g.solovev5.m.task221.pizzeria.warehouse.PizzaWarehouse;

/**
 * A dummy version of PizzaWarehouse.
 */
public class DummyPizzaWarehouse extends PizzaWarehouse {
    public DummyPizzaWarehouse(int capacity) {
        super(capacity);
    }

    /**
     * Checks if this warehouse contains the given order.
     *
     * @param order the order to check
     * @return {@code true} if the given order is in this warehouse, {@code false} otherwise
     */
    public boolean has(Order order) {
        return orders.contains(order);
    }
}
