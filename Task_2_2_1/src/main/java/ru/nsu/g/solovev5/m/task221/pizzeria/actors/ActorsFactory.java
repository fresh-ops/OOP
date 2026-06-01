package ru.nsu.g.solovev5.m.task221.pizzeria.actors;

import java.util.List;
import ru.nsu.g.solovev5.m.task221.logging.OrderLogger;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.OrderQueue;
import ru.nsu.g.solovev5.m.task221.pizzeria.warehouse.PizzaWarehouse;

/**
 * A pizzeria employees factory.
 */
public class ActorsFactory {
    private final OrderQueue orders;
    private final PizzaWarehouse warehouse;
    private final OrderLogger logger;

    /**
     * Creates a new ActorsFactory.
     *
     * @param orders the orders queue
     * @param warehouse the pizzerias warehouse
     * @param logger the order status logger
     */
    public ActorsFactory(OrderQueue orders, PizzaWarehouse warehouse, OrderLogger logger) {
        this.orders = orders;
        this.warehouse = warehouse;
        this.logger = logger;
    }

    /**
     * Generates new bakers with given speeds.
     *
     * @param bakersCookingSpeeds the speeds with which bakers will cook
     */
    public List<Baker> createBakers(List<Integer> bakersCookingSpeeds) {
        return bakersCookingSpeeds.stream()
            .map(speed -> new Baker(speed, orders, warehouse, logger))
            .toList();
    }

    /**
     * Generates new couriers with given bag capacities.
     *
     * @param courierBagCapacities the capacities of new couriers bags
     */
    public List<Courier> createCouriers(List<Integer> courierBagCapacities) {
        return courierBagCapacities.stream()
            .map(capacity -> new Courier(warehouse, capacity, logger))
            .toList();
    }
}
