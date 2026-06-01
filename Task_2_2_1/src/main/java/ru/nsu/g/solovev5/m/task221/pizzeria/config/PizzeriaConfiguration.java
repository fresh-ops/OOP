package ru.nsu.g.solovev5.m.task221.pizzeria.config;

import java.util.List;

/**
 * A configuration object that holds information about pizzeria.
 */
public record PizzeriaConfiguration(
    int workingTime,
    int warehouseCapacity,
    List<Integer> bakersCookingSpeeds,
    List<Integer> couriersBagCapacities
) {
    public static final int DEFAULT_WORKING_TIME = 1_000;
    public static final int DEFAULT_WAREHOUSE_CAPACITY = 10;
    public static final List<Integer> DEFAULT_BAKERS_COOKING_SPEED = List.of(5, 5, 5);
    public static final List<Integer> DEFAULT_COURIER_BAG_CAPACITIES = List.of(2, 2, 2);


    /**
     * Loads a configuration with default values.
     *
     * @return a new configuration
     */
    public static PizzeriaConfiguration loadDefault() {
        return new PizzeriaConfiguration(
            DEFAULT_WORKING_TIME,
            DEFAULT_WAREHOUSE_CAPACITY,
            DEFAULT_BAKERS_COOKING_SPEED,
            DEFAULT_COURIER_BAG_CAPACITIES);
    }
}
