package ru.nsu.g.solovev5.m.task221.pizzeria.reaper;


import java.util.List;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.Order;

/**
 * An orders owner that can release them.
 */
public interface Reapable {
    /**
     * Collects all owned orders.
     *
     * @return orders in this reapable
     */
    List<Order> collect();
}
