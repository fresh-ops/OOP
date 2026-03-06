package ru.nsu.g.solovev5.m.task221.pizzeria.actors;

import ru.nsu.g.solovev5.m.task221.pizzeria.orders.OrderQueue;
import ru.nsu.g.solovev5.m.task221.pizzeria.warehouse.PizzaWarehouse;

/**
 * This is a baker, he works three days without a salary.
 */
public class Baker extends Thread {
    private final int cookingSpeed;
    private final OrderQueue orders;
    private final PizzaWarehouse warehouse;

    /**
     * Creates a new baker with the given cooking speed.
     *
     * @param cookingSpeed the speed of cooking a pizza
     * @param orders the queue of orders
     * @param warehouse the pizza warehouse
     */
    public Baker(int cookingSpeed, OrderQueue orders, PizzaWarehouse warehouse) {
        this.cookingSpeed = cookingSpeed;
        this.orders = orders;
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                cook();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Cooks the order from queue and puts it to the warehouse.
     */
    private void cook() throws InterruptedException {
        var order = orders.take();
        order.promoteStatus();
        var pizza = order.getPizza();

        while (!pizza.isCooked()) {
            pizza.cookFor(cookingSpeed);
        }

        order.promoteStatus();
        warehouse.put(order);
    }
}
