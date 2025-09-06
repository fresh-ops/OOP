package ru.nsu.g.solovev5.m.task112.cards;

public enum CardRank {
    TWO ("ДВОЙКА", 2),
    THREE ("ТРОЙКА", 3),
    FOUR ("ЧЕТВЁРКА", 4),
    FIVE ("ПЯТЁРКА", 5),
    SIX ("ШЕСТЁРКА", 6),
    SEVEN ("СЕМЁРКА", 7),
    EIGHT ("ВОСЬМЁРКА", 8),
    NINE ("ДЕВЯТКА", 9),
    TEN ("ДЕСЯТКА", 10),
    JACK ("ВАЛЕТ", 10),
    QUEEN ("ДАМА", 10),
    KING ("КОРОЛЬ", 10),
    ACE ("ТУЗ", 11);

    private final String name;
    private final int cost;

    CardRank(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return name;
    }
}
