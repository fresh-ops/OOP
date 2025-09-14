package ru.nsu.g.solovev5.m.task112.cards;

public enum CardSuit {
    CLUBS("ТРЕФЫ"),
    DIAMONDS("БУБИ"),
    HEARTS("ЧЕРВИ"),
    SPADES("ПИКИ");

    private final String name;

    CardSuit(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
