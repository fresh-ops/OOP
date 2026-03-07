package ru.nsu.g.solovev5.m.task221.pizzeria.warehouse;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.Order;

/**
 * A limited warehouse for holding pizza before delivery.
 */
public class PizzaWarehouse {
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();
    private final int capacity;
    protected final List<Order> orders;

    /**
     * Creates a new warehouse with {@code capacity} places for pizza.
     *
     * @param capacity the amount of pizza a new warehouse should be ready to hold
     */
    public PizzaWarehouse(int capacity) {
        this.capacity = capacity;
        orders = new LinkedList<>();
    }

    /**
     * Puts a new order to this warehouse.
     *
     * @param order the order to put
     */
    public void put(Order order) throws InterruptedException {
        lock.lock();
        try {
            while (orders.size() >= capacity) {
                notFull.await();
            }

            orders.add(order);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Takes at most {@code amount} number of orders from this warehouse.
     *
     * @param amount the maximum desired amount of pizza
     * @return the list of taken orders
     * @throws IllegalArgumentException if the amount is less then or equal to zero
     */
    public List<Order> takeAtMost(int amount) throws InterruptedException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        lock.lock();
        var takenOrders = new ArrayList<Order>(amount);
        try {
            while (orders.isEmpty()) {
                notEmpty.await();
            }
            for (int i = 0; i < amount && !orders.isEmpty(); i++) {
                takenOrders.add(orders.remove(0));
            }
            notFull.signalAll();

            return takenOrders;
        } finally {
            lock.unlock();
        }
    }
}
