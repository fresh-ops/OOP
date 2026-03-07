package ru.nsu.g.solovev5.m.task221.pizzeria.actors;

import ru.nsu.g.solovev5.m.task221.pizzeria.orders.Order;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.OrderQueue;
import ru.nsu.g.solovev5.m.task221.pizzeria.warehouse.PizzaWarehouse;

/**
 * This is a baker, he works three days without a salary.
 */
public class Baker implements Runnable {
    private final int cookingSpeed;
    private final OrderQueue orders;
    private final PizzaWarehouse warehouse;

    private Order interruptedOrder;

    /**
     * Creates a new baker with the given cooking speed.
     *
     * @param cookingSpeed the speed of cooking a pizza
     * @param orders       the queue of orders
     * @param warehouse    the pizza warehouse
     */
    public Baker(int cookingSpeed, OrderQueue orders, PizzaWarehouse warehouse) {
        this.cookingSpeed = cookingSpeed;
        this.orders = orders;
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        this.interruptedOrder = null;

        Order order;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                order = orders.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            order.promoteStatus();
            cook(order);
            order.promoteStatus();

            try {
                warehouse.put(order);
            } catch (InterruptedException e) {
                interruptedOrder = order;
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Checks if this baker has interrupted order.
     *
     * @return {@code true} if there is an interrupted order, {@code false} otherwise
     */
    public boolean hasInterruptedOrder() {
        return interruptedOrder != null;
    }

    /**
     * Returns the interrupted order.
     *
     * @return the interrupted order.
     */
    public Order getInterruptedOrder() {
        return interruptedOrder;
    }

    /**
     * Cooks the given order.
     *
     * @param order the order to cook
     */
    private void cook(Order order) {
        var pizza = order.getPizza();

        while (!pizza.isCooked()) {
            pizza.cookFor(cookingSpeed);
        }
    }
}
