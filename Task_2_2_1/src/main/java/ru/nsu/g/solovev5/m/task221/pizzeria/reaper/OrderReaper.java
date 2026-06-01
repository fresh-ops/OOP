package ru.nsu.g.solovev5.m.task221.pizzeria.reaper;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.Order;

/**
 * An order collector.
 */
public class OrderReaper {
    private final List<Order> collected;

    /**
     * Creates a new OrderReaper.
     */
    public OrderReaper() {
        this.collected = new ArrayList<>();
    }

    /**
     * Collects the orders.
     *
     * @param reapable the orders owner
     */
    public void reap(Reapable reapable) {
        collected.addAll(reapable.collect());
    }

    /**
     * Returns collected orders.
     *
     * @return collected orders
     */
    public List<Order> getCollected() {
        return collected;
    }
}
