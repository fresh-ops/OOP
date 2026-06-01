package ru.nsu.g.solovev5.m.task221.pizzeria.pizza;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PizzaTest {
    @Test
    void isCooked_should_returnFalse_whenNotEnoughCooked() {
        var pizza = Pizza.fromMenu(PizzaInMenu.PEPPERONI);
        var sum = 0;
        for (var i = 0; sum + i < PizzaInMenu.PEPPERONI.getCookingTime(); i++) {
            pizza.cookFor(i);
            assertFalse(pizza.isCooked());
            sum += i;
        }
    }

    @Test
    void isCooked_should_returnTrue_whenEnoughCooked() {
        var pizza = Pizza.fromMenu(PizzaInMenu.MARGHERITA);
        pizza.cookFor(PizzaInMenu.MARGHERITA.getCookingTime());

        assertTrue(pizza.isCooked());
    }
}