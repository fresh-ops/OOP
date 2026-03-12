package ru.nsu.g.solovev5.m.task221.pizzeria.actors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task221.pizzeria.DummyOrderLogger;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.Order;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.OrderStatus;
import ru.nsu.g.solovev5.m.task221.pizzeria.pizza.PizzaInMenu;

class CourierTest {
    @Test
    void should_takeAllOrdersFromWarehouse_when_thereAreLessOrEqual() throws InterruptedException {
        var warehouse = new DummyPizzaWarehouse(10);
        var logger = new DummyOrderLogger();
        var courier = new Courier(warehouse, 10, logger);
        var orders = new ArrayList<Order>();

        for (var i = 0; i < 10; i++) {
            var order = new Order(PizzaInMenu.PEPPERONI, i);
            while (order.getStatus() != OrderStatus.IN_WAREHOUSE) {
                order.promoteStatus();
            }
            warehouse.put(order);
            orders.add(order);
        }
        var thread = new Thread(courier);
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
        thread.join();

        for (var order : orders) {
            assertEquals(OrderStatus.DONE, order.getStatus());
            assertFalse(warehouse.has(order));
        }
    }

    @Test
    void getBagCapacity_should_returnSameValueAsPassed() {
        var warehouse = new DummyPizzaWarehouse(10);
        var logger = new DummyOrderLogger();
        var courier = new Courier(warehouse, 10, logger);

        assertEquals(10, courier.getBagCapacity());
        assertEquals(10, courier.getBagCapacity());
    }
}