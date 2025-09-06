package ru.nsu.g.solovev5.m.task112.cards;

public record Card(CardSuit suit, CardRank rank) {
    @Override
    public String toString() {
        return String.format("%s %s", rank, suit);
    }
}
