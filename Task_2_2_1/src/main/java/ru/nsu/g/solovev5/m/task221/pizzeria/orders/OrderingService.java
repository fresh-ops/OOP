package ru.nsu.g.solovev5.m.task221.pizzeria.orders;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import ru.nsu.g.solovev5.m.task221.pizzeria.pizza.PizzaInMenu;

/**
 * A service that generates orders and puts theme into the queue.
 */
public class OrderingService {
    private static final PizzaInMenu[] MENU = PizzaInMenu.values();
    private static final int MIN_DELAY = 1;
    private static final int MAX_DELAY = 100;

    private static final AtomicInteger nextOrderId = new AtomicInteger(0);

    private final AtomicBoolean running = new AtomicBoolean(false);
    private final Random random = new Random();
    private final OrderQueue orderQueue;

    private ScheduledExecutorService scheduler;

    /**
     * Creates a new OrderingService that puts new orders to the given queue.
     *
     * @param orderQueue a queue to put orders
     */
    public OrderingService(final OrderQueue orderQueue) {
        this.orderQueue = orderQueue;
        scheduler = createScheduler();
    }

    /**
     * Starts the orders generating.
     */
    public void start() {
        if (running.compareAndSet(false, true)) {
            if (scheduler.isShutdown() || scheduler.isTerminated()) {
                scheduler = createScheduler();
            }
            scheduleOrder();
        }
    }

    /**
     * Stops the orders generating.
     */
    public void stop() {
        if (running.compareAndSet(true, false)) {
            scheduler.shutdown();
        }
    }

    /**
     * Creates a new thread scheduler.
     *
     * @return a new thread scheduler
     */
    private ScheduledExecutorService createScheduler() {
        return Executors.newSingleThreadScheduledExecutor(task -> {
            var thread = new Thread(task);
            thread.setDaemon(true);
            return thread;
        });
    }

    /**
     * If this service is running, schedules the new order with random delay(1-99ms).
     */
    private void scheduleOrder() {
        if (!running.get()) {
            return;
        }

        var delay = random.nextInt(MIN_DELAY,  MAX_DELAY);
        scheduler.schedule(() -> {
                var newOrder = createOrder();
                newOrder.promoteStatus();
                orderQueue.put(newOrder);

                scheduleOrder();
            }, delay, TimeUnit.MILLISECONDS
        );
    }

    /**
     * Creates a new order with autoincrement id.
     *
     * @return a new order
     */
    private Order createOrder() {
        var pizzaIndex = random.nextInt(MENU.length);

        return new Order(
            MENU[pizzaIndex],
            nextOrderId.getAndIncrement()
        );
    }
}
