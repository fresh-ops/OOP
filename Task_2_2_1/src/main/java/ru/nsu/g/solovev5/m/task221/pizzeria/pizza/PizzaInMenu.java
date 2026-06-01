package ru.nsu.g.solovev5.m.task221.pizzeria.pizza;

/**
 * Different pizza positions in menu.
 */
public enum PizzaInMenu {
    PEPPERONI(100), MARGHERITA(50);

    private final int cookingTime;

    PizzaInMenu(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    /**
     * Returns the time required to cook this pizza.
     *
     * @return the cooking time of this pizza
     */
    public int getCookingTime() {
        return cookingTime;
    }
}
