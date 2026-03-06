package ru.nsu.g.solovev5.m.task221.pizzeria.pizza;

/**
 * A tasty cheesy pizza. Or just simulation of one.
 */
public class Pizza {
    private final int cookingTime;
    private int currentCooked = 0;

    /**
     * Creates a new Pizza for the given menu position.
     *
     * @param menu the position with the pizza description
     * @return a new pizza
     */
    public static Pizza fromMenu(PizzaInMenu menu) {
        return new Pizza(
            menu.getCookingTime()
        );
    }

    /**
     * Creates a new Pizza with the given cooking time.
     *
     * @param cookingTime a time needed to cook this pizza
     */
    private Pizza(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    /**
     * Checks whether this pizza is done.
     *
     * @return {@code true} if this pizza was cooked enough time to be done, {@code false} otherwise
     */
    public boolean isCooked() {
        return  currentCooked >= cookingTime;
    }

    /**
     * Cooks this pizza for given amount of time.
     *
     * @param time the time pizza should be cooked for
     */
    public void cookFor(int time) {
        currentCooked += time;
    }
}
