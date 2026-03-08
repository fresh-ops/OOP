package ru.nsu.g.solovev5.m.task221.pizzeria.actors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task221.pizzeria.DummyOrderLogger;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.Order;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.OrderQueue;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.OrderStatus;
import ru.nsu.g.solovev5.m.task221.pizzeria.pizza.PizzaInMenu;
import ru.nsu.g.solovev5.m.task221.pizzeria.warehouse.PizzaWarehouse;

class BakerTest {
    @Test
    void hasInterruptedOrder_should_returnFalse_when_nothingWasCooking() {
        var orders = new OrderQueue();
        var warehouse = new PizzaWarehouse(10);
        var logger = new DummyOrderLogger();
        var baker = new Baker(10, orders, warehouse, logger);

        assertFalse(baker.hasInterruptedOrder());
        assertNull(baker.getInterruptedOrder());
    }

    @Test
    void hasInterruptedOrder_should_returnTrue_when_itIsInterrupted() throws InterruptedException {
        var orders = new OrderQueue();
        var warehouse = new PizzaWarehouse(10);
        var logger = new DummyOrderLogger();
        var baker = new Baker(0, orders, warehouse, logger);
        var order = new Order(PizzaInMenu.PEPPERONI, 0);

        orders.put(order);
        var thread = new Thread(baker);
        thread.start();
        Thread.sleep(100);
        thread.interrupt();
        thread.join();

        assertTrue(baker.hasInterruptedOrder());
        assertEquals(order, baker.getInterruptedOrder());
    }

    @Test
    void should_promoteOrderStatusAndPutToWarehouse_when_cookingDone() throws InterruptedException {
        var orders = new OrderQueue();
        var warehouse = new DummyPizzaWarehouse(10);
        var logger = new DummyOrderLogger();
        var baker = new Baker(PizzaInMenu.PEPPERONI.getCookingTime(), orders, warehouse, logger);
        var order = new Order(PizzaInMenu.PEPPERONI, 0);

        order.promoteStatus();
        orders.put(order);
        var thread = new Thread(baker);
        thread.start();
        Thread.sleep(100);
        thread.interrupt();
        thread.join();

        assertFalse(baker.hasInterruptedOrder());
        assertNull(baker.getInterruptedOrder());
        assertEquals(OrderStatus.IN_WAREHOUSE, order.getStatus());
        assertTrue(warehouse.has(order));
    }

    @Test
    void getCookingSpeed_should_returnSameValueAsPassed() {
        var orders = new OrderQueue();
        var warehouse = new DummyPizzaWarehouse(10);
        var logger = new DummyOrderLogger();
        var baker = new Baker(10, orders, warehouse, logger);

        assertEquals(10, baker.getCookingSpeed());
        assertEquals(10, baker.getCookingSpeed());
    }
}