package ru.nsu.g.solovev5.m.task221.logging;

import ru.nsu.g.solovev5.m.task221.pizzeria.orders.Order;

/**
 * An object that logs order statuses.
 */
public interface OrderLogger {
    /**
     * Logs the order status.
     *
     * @param order the order to log information about
     */
    void log(Order order);
}
