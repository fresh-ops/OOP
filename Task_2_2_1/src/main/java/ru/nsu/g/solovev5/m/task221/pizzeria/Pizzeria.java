package ru.nsu.g.solovev5.m.task221.pizzeria;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.g.solovev5.m.task221.logging.OrderLogger;
import ru.nsu.g.solovev5.m.task221.pizzeria.actors.Baker;
import ru.nsu.g.solovev5.m.task221.pizzeria.actors.Courier;
import ru.nsu.g.solovev5.m.task221.pizzeria.config.PizzeriaConfiguration;
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
     * @param config the configuration of the new pizzeria
     */
    public Pizzeria(PizzeriaConfiguration config, OrderLogger logger) {
        this.logger = logger;
        orderQueue = new OrderQueue();
        warehouse = new PizzaWarehouse(config.getWarehouseCapacity());
        employees = new ArrayList<>();
        generateBakers(config.getBakersCookingSpeeds());
        generateCouriers(config.getCouriersBagCapacities());
        stopped = true;
    }

    /**
     * Generates new bakers with given speeds.
     *
     * @param bakersCookingSpeeds the speeds with which bakers will cook
     */
    private void generateBakers(List<Integer> bakersCookingSpeeds) {
        for (var speed : bakersCookingSpeeds) {
            var baker = new Baker(speed, orderQueue, warehouse, logger);
            employees.add(new Thread(baker));
        }
    }

    /**
     * Generates new couriers with given bag capacities.
     *
     * @param courierBagCapacities the capacities of new couriers bags
     */
    private void generateCouriers(List<Integer> courierBagCapacities) {
        for (var bag : courierBagCapacities) {
            var courier = new Courier(warehouse, bag, logger);
            employees.add(new Thread(courier));
        }
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
