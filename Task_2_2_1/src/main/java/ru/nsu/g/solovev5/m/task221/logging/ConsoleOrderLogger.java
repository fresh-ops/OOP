package ru.nsu.g.solovev5.m.task221.logging;

import ru.nsu.g.solovev5.m.task221.pizzeria.orders.Order;

/**
 * An object that writes logs to the standard output.
 */
public class ConsoleOrderLogger implements OrderLogger {
    @Override
    public void log(Order order) {
        System.out.println("Order#" + order.getId() + " status: " + order.getStatus());
    }
}
