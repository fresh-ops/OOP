package ru.nsu.g.solovev5.m.task221.pizzeria.orders;

import ru.nsu.g.solovev5.m.task221.pizzeria.pizza.PizzaInMenu;

/**
 * A pizza order.
 */
public class Order {
    private final PizzaInMenu pizza;
    private final int id;
    private OrderStatus status;

    /**
     * Creates a new Order.
     *
     * @param pizza the ordered pizza
     * @param id the order id
     */
    public Order(PizzaInMenu pizza, int id) {
        this.pizza = pizza;
        this.id = id;
        this.status = OrderStatus.NEW;
    }

    /**
     * Promotes this order status.
     */
    public synchronized void promoteStatus() {
        status = status.next();
    }

    /**
     * Returns the status of this order.
     *
     * @return this order status
     */
    public synchronized OrderStatus getStatus() {
        return status;
    }

    /**
     * Returns the id of this order.
     *
     * @return this order id
     */
    public int getId() {
        return id;
    }

    /**
     * Return the ordered pizza type.
     *
     * @return the ordered pizza type
     */
    public PizzaInMenu getPizzaType() {
        return pizza;
    }
}
