package ru.nsu.g.solovev5.m.task221.pizzeria.actors;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task221.pizzeria.DummyOrderLogger;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.OrderQueue;

class ActorsFactoryTest {
    @Test
    void createBakers_should_createBakersWithPassedParameters() {
        var cookingSpeeds = List.of(1, 2, 3, 4, 5);
        var factory = new ActorsFactory(
            new OrderQueue(),
            new DummyPizzaWarehouse(10),
            new DummyOrderLogger()
        );

        var bakers =  factory.createBakers(cookingSpeeds);

        for (var i = 0; i < cookingSpeeds.size(); i++) {
            assertEquals(cookingSpeeds.get(i), bakers.get(i).getCookingSpeed());
        }
    }

    @Test
    void createCouriers_should_createBakersWithPassedParameters() {
        var bagCapacities = List.of(1, 2, 3, 4, 5);
        var factory = new ActorsFactory(
            new OrderQueue(),
            new DummyPizzaWarehouse(10),
            new DummyOrderLogger()
        );

        var couriers =  factory.createCouriers(bagCapacities);

        for (var i = 0; i < bagCapacities.size(); i++) {
            assertEquals(bagCapacities.get(i), couriers.get(i).getBagCapacity());
        }
    }
}