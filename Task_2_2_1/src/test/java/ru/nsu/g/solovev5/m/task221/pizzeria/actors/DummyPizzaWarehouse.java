package ru.nsu.g.solovev5.m.task221.pizzeria.actors;

import ru.nsu.g.solovev5.m.task221.pizzeria.orders.Order;
import ru.nsu.g.solovev5.m.task221.pizzeria.warehouse.PizzaWarehouse;

public class DummyPizzaWarehouse extends PizzaWarehouse {
    public DummyPizzaWarehouse(int capacity) {
        super(capacity);
    }

    public boolean has(Order order) {
        return orders.contains(order);
    }
}
