package ru.nsu.g.solovev5.m.task221.pizzeria;

import ru.nsu.g.solovev5.m.task221.logging.ConsoleOrderLogger;
import ru.nsu.g.solovev5.m.task221.logging.OrderLogger;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.OrderQueue;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.OrderingService;

/**
 * A pizzeria simulation.
 */
public class Pizzeria {
    private final OrderQueue orderQueue;
    private OrderingService orderingService;
    private final OrderLogger logger;
    private boolean stopped;

    /**
     * Creates a new Pizzeria.
     */
    public Pizzeria() {
        logger = new ConsoleOrderLogger();
        orderQueue = new OrderQueue();
        stopped = true;
    }

    /**
     * Begins the pizzeria working.
     */
    public void work() {
        orderingService = new OrderingService(orderQueue, logger);
        orderingService.start();
        stopped = false;
    }

    /**
     * Stops the pizzeria working.
     */
    public void stop() {
        orderingService.stop();
        stopped = true;
    }

    /**
     * Checks whether this pizzeria is stopped.
     *
     * @return {@code true} if this pizzeria is not working, {@code false} otherwise
     */
    public boolean isStopped() {
        return stopped;
    }
}
