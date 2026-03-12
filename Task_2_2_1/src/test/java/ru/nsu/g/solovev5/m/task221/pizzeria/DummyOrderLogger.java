package ru.nsu.g.solovev5.m.task221.pizzeria;

import ru.nsu.g.solovev5.m.task221.logging.OrderLogger;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.Order;

/**
 * A dummy version of OrderLogger.
 */
public class DummyOrderLogger implements OrderLogger {
    @Override
    public void log(Order order) {
    }
}
