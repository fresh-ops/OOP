package ru.nsu.g.solovev5.m.task221.pizzeria;

import java.util.List;
import ru.nsu.g.solovev5.m.task221.logging.OrderLogger;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.OrderQueue;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.OrderingService;
import ru.nsu.g.solovev5.m.task221.pizzeria.warehouse.PizzaWarehouse;

/**
 * A pizzeria simulation.
 */
public class Pizzeria {
    private final OrderQueue orderQueue;
    private final PizzaWarehouse warehouse;
    private final List<Thread> employees;
    private final OrderLogger logger;

    private boolean stopped;
    private OrderingService orderingService;

    /**
     * Creates a new Pizzeria.
     *
     * @param orderQueue the queue of orders
     * @param warehouse the pizza warehouse
     * @param logger the order status logger
     * @param employees bakers and couriers
     */
    public Pizzeria(OrderQueue orderQueue, PizzaWarehouse warehouse, OrderLogger logger,
                    List<Runnable> employees) {
        this.logger = logger;
        this.orderQueue = orderQueue;
        this.warehouse = warehouse;
        this.employees = employees.stream().map(Thread::new).toList();

        stopped = true;
    }

    /**
     * Begins the pizzeria working.
     */
    public void work() {
        orderingService = new OrderingService(orderQueue, logger);
        orderingService.start();

        employees.forEach(Thread::start);

        stopped = false;
    }

    /**
     * Stops the pizzeria working.
     */
    public void stop() throws InterruptedException {
        orderingService.stop();

        for (var employee : employees) {
            employee.interrupt();
            employee.join();
        }

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
