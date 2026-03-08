package ru.nsu.g.solovev5.m.task221.pizzeria.config;

import java.util.List;

/**
 * A configuration object that holds information about pizzeria.
 */
public class PizzeriaConfiguration {
    public static final int DEFAULT_WORKING_TIME = 1_000;
    public static final int DEFAULT_WAREHOUSE_CAPACITY = 10;
    public static final List<Integer> DEFAULT_BAKERS_COOKING_SPEED = List.of(5, 5, 5);
    public static final List<Integer> DEFAULT_COURIER_BAG_CAPACITIES = List.of(2, 2, 2);

    private final int workingTime;
    private final int warehouseCapacity;
    private final List<Integer> bakersCookingSpeeds;
    private final List<Integer> couriersBagCapacities;

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

    private PizzeriaConfiguration(int workingTime, int warehouseCapacity,
                                  List<Integer> bakersCookingSpeeds,
                                  List<Integer> couriersBagCapacities) {
        this.workingTime = workingTime;
        this.warehouseCapacity = warehouseCapacity;
        this.bakersCookingSpeeds = bakersCookingSpeeds;
        this.couriersBagCapacities = couriersBagCapacities;
    }

    /**
     * Returns the pizzeria working time.
     *
     * @return the pizzeria working time
     */
    public int getWorkingTime() {
        return workingTime;
    }

    /**
     * Returns the warehouse capacity.
     *
     * @return the warehouse capacity
     */
    public int getWarehouseCapacity() {
        return warehouseCapacity;
    }

    /**
     * Returns the bakers cooking speeds.
     *
     * @return the bakers cooking speeds
     */
    public List<Integer> getBakersCookingSpeeds() {
        return bakersCookingSpeeds;
    }

    /**
     * Returns the couriers bag capacities.
     *
     * @return the couriers bag capacities
     */
    public List<Integer> getCouriersBagCapacities() {
        return couriersBagCapacities;
    }
}
