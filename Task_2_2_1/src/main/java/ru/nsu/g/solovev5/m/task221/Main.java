package ru.nsu.g.solovev5.m.task221;

import java.util.ArrayList;
import ru.nsu.g.solovev5.m.task221.logging.ConsoleOrderLogger;
import ru.nsu.g.solovev5.m.task221.pizzeria.Pizzeria;
import ru.nsu.g.solovev5.m.task221.pizzeria.actors.ActorsFactory;
import ru.nsu.g.solovev5.m.task221.pizzeria.config.PizzeriaConfiguration;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.OrderQueue;
import ru.nsu.g.solovev5.m.task221.pizzeria.warehouse.PizzaWarehouse;

/**
 * The program starter.
 */
public class Main {
    /**
     * The program entry point.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        var arguments = CommandLineArguments.parse(args);
        var config = PizzeriaConfiguration.loadDefault();

        var orders = new OrderQueue();
        var warehouse = new PizzaWarehouse(config.getWarehouseCapacity());
        var logger = new ConsoleOrderLogger();
        var factory = new ActorsFactory(orders, warehouse, logger);

        var employees = new ArrayList<Runnable>();
        employees.addAll(factory.createBakers(config.getBakersCookingSpeeds()));
        employees.addAll(factory.createCouriers(config.getCouriersBagCapacities()));

        var pizzeria = new Pizzeria(orders, warehouse, logger, employees);
        pizzeria.work();
        try {
            Thread.sleep(config.getWorkingTime());
            pizzeria.stop();
        } catch (InterruptedException e) {
            System.out.println("External interruption. Stopping...");
        }
    }
}