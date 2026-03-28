package ru.nsu.g.solovev5.m.task231.domain.valueobjects;

/**
 * A type of food.
 */
public enum FoodType {
    NORMAL(1, 10), ROTTEN(0, -10);

    private final int growthSize;
    private final int score;

    FoodType(int growthSize, int score) {
        this.growthSize = growthSize;
        this.score = score;
    }

    /**
     * Returns a growth size for this food type.
     *
     * @return a growth size for this food type
     */
    public int getGrowthSize() {
        return growthSize;
    }

    /**
     * Returns a score given for this food type.
     *
     * @return a score given for this food type
     */
    public int getScore() {
        return score;
    }
}
