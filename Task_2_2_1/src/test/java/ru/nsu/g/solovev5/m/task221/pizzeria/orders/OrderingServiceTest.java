package ru.nsu.g.solovev5.m.task221.pizzeria.orders;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrderingServiceTest {
    @Test
    void should_incrementIds_when_generatingOrders() throws InterruptedException {
        var orders = new OrderQueue();
        var service = new OrderingService(orders, new DummyOrderLogger());
        service.start();
        Thread.sleep(1_000);
        service.stop();

        assertFalse(orders.isEmpty());
        var previousId = orders.take().getId();
        while (!orders.isEmpty()) {
            var id =  orders.take().getId();
            assertEquals(1, id - previousId);
            previousId = id;
        }
    }
}