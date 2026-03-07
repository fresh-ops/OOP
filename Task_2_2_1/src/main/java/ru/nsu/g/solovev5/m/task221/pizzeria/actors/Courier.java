package ru.nsu.g.solovev5.m.task221.pizzeria.actors;

import java.util.List;
import ru.nsu.g.solovev5.m.task221.logging.OrderLogger;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.Order;
import ru.nsu.g.solovev5.m.task221.pizzeria.warehouse.PizzaWarehouse;

/**
 * A guy with a fairly promising job.
 */
public class Courier implements Runnable {
    private final PizzaWarehouse warehouse;
    private final int bagCapacity;
    private final OrderLogger logger;

    /**
     * Creates a new courier.
     *
     * @param warehouse   the warehouse where to take orders
     * @param bagCapacity the maximum amount of pizza courier can deliver at once
     * @param logger      the logger to log actions
     */
    public Courier(PizzaWarehouse warehouse, int bagCapacity, OrderLogger logger) {
        this.warehouse = warehouse;
        this.bagCapacity = bagCapacity;
        this.logger = logger;
    }

    @Override
    public void run() {
        List<Order> orders = List.of();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                orders = warehouse.takeAtMost(bagCapacity);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            updateStatuses(orders);
            deliver(orders);
        }
    }

    /**
     * Updates the order statuses.
     *
     * @param orders the orders to promote
     */
    private void updateStatuses(List<Order> orders) {
        orders.forEach(order -> {
            order.promoteStatus();
            logger.log(order);
        });
    }

    /**
     * Delivers the orders.
     *
     * @param orders the orders to deliver
     */
    private void deliver(List<Order> orders) {
        orders.forEach(order -> {
            order.promoteStatus();
            logger.log(order);
        });
    }
}
