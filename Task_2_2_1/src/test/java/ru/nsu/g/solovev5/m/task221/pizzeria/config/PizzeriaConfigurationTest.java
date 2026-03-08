package ru.nsu.g.solovev5.m.task221.pizzeria.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PizzeriaConfigurationTest {
    @Test
    void loadDefault_should_assignDefaultValues() {
        var config = PizzeriaConfiguration.loadDefault();

        assertEquals(
            PizzeriaConfiguration.DEFAULT_WORKING_TIME,
            config.getWorkingTime()
        );
        assertEquals(
            PizzeriaConfiguration.DEFAULT_WAREHOUSE_CAPACITY,
            config.getWarehouseCapacity()
        );
        assertEquals(
            PizzeriaConfiguration.DEFAULT_BAKERS_COOKING_SPEED,
            config.getBakersCookingSpeeds()
        );
        assertEquals(
            PizzeriaConfiguration.DEFAULT_COURIER_BAG_CAPACITIES,
            config.getCouriersBagCapacities()
        );
    }
}