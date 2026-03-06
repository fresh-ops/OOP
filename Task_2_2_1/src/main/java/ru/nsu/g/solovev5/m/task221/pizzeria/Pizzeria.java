package ru.nsu.g.solovev5.m.task221.pizzeria;

import ru.nsu.g.solovev5.m.task221.pizzeria.orders.OrderQueue;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.OrderingService;

/**
 * A pizzeria simulation.
 */
public class Pizzeria {
    private final OrderQueue orderQueue;
    private OrderingService orderingService;
    private boolean stopped;

    /**
     * Creates a new Pizzeria.
     */
    public Pizzeria() {
        orderQueue = new OrderQueue();
        stopped = true;
    }

    /**
     * Begins the pizzeria working.
     */
    public void work() {
        orderingService = new OrderingService(orderQueue);
        orderingService.start();
        stopped = false;
    }

    public void stop() throws InterruptedException {
        orderingService.stop();
        stopped = true;
    }

    public boolean isStopped() {
        return stopped;
    }
}
