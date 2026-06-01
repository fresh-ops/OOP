package ru.nsu.g.solovev5.m.task221.pizzeria.orders;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import ru.nsu.g.solovev5.m.task221.pizzeria.reaper.Reapable;

/**
 * A queue of pizza orders.
 */
public class OrderQueue implements Reapable {
    private final List<Order> queue;
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();

    /**
     * Creates a new empty OrderQueue.
     */
    public OrderQueue() {
        queue = new LinkedList<>();
    }

    @Override
    public List<Order> collect() {
        lock.lock();
        try {
            var collected = new ArrayList<Order>();
            while (!queue.isEmpty()) {
                collected.add(queue.remove(0));
            }

            return collected;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Puts a new order to this queue.
     *
     * @param order a new order in this queue
     */
    public void put(Order order) {
        lock.lock();

        queue.add(order);

        notEmpty.signal();
        lock.unlock();
    }

    /**
     * Takes the pending order from queue.
     *
     * @return an order from queue
     */
    public Order take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();
            }

            return queue.remove(0);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Checks if there is no orders in queue.
     *
     * @return {@code true} if there is no orders in queue, {@code false} otherwise
     */
    public boolean isEmpty() {
        lock.lock();

        try {
            return queue.isEmpty();
        } finally {
            lock.unlock();
        }
    }
}
