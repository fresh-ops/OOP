package ru.nsu.g.solovev5.m.task221.pizzeria;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task221.pizzeria.actors.DummyPizzaWarehouse;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.OrderQueue;

class PizzeriaTest {
    @Test
    void isStopped_should_returnTrue_when_pizzeriaIsNotWorking() {
        var orders = new OrderQueue();
        var warehouse = new DummyPizzaWarehouse(10);
        var logger = new DummyOrderLogger();
        List<Runnable> employees = List.of();
        var pizzeria = new Pizzeria(orders, warehouse, logger, employees);

        assertTrue(pizzeria.isStopped());
        assertDoesNotThrow(pizzeria::stop);
        assertTrue(pizzeria.isStopped());
    }

    @Test
    void work_should_notFail_when_calledTwice() {
        var orders = new OrderQueue();
        var warehouse = new DummyPizzaWarehouse(10);
        var logger = new DummyOrderLogger();
        List<Runnable> employees = List.of();
        var pizzeria = new Pizzeria(orders, warehouse, logger, employees);

        assertDoesNotThrow(pizzeria::work);
        assertDoesNotThrow(pizzeria::work);
        assertFalse(pizzeria.isStopped());
        assertDoesNotThrow(pizzeria::stop);
        assertTrue(pizzeria.isStopped());
    }
}