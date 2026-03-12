package ru.nsu.g.solovev5.m.task221.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task221.pizzeria.orders.Order;
import ru.nsu.g.solovev5.m.task221.pizzeria.pizza.PizzaInMenu;

class ConsoleOrderLoggerTest {
    @Test
    void log_should_printOutputToConsole() {
        var logger = new ConsoleOrderLogger();

        var baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        logger.log(new Order(PizzaInMenu.PEPPERONI, 0));
        logger.log(new Order(PizzaInMenu.MARGHERITA, 1));
        logger.log(new Order(PizzaInMenu.PEPPERONI, 2));
        logger.log(new Order(PizzaInMenu.MARGHERITA, 3));

        assertEquals("""
            Order#0 status: NEW
            Order#1 status: NEW
            Order#2 status: NEW
            Order#3 status: NEW
            """, baos.toString());
    }
}