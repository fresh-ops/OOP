package ru.nsu.g.solovev5.m.task112.cards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void checkStringRepresentation() {
        var card = new Card(CardSuit.CLUBS, CardRank.EIGHT);
        assertEquals("ВОСЬМЁРКА ТРЕФЫ", card.toString());
    }

    @Test
    void suit() {
        var card = new Card(CardSuit.HEARTS, CardRank.ACE);
        assertEquals(CardSuit.HEARTS, card.suit());
    }

    @Test
    void rank() {
        var card = new Card(CardSuit.SPADES, CardRank.QUEEN);
        assertEquals(CardRank.QUEEN, card.rank());
    }
}