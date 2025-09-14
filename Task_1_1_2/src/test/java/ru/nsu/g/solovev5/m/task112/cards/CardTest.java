package ru.nsu.g.solovev5.m.task112.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CardTest {
    @Test
    void checkDefaultConstructorHiddenness() {
        var card = new Card(CardSuit.SPADES, CardRank.SIX);

        assertFalse(card.isHidden());
    }

    @Test
    void checkUnhiddenCard() {
        var card = new Card(CardSuit.CLUBS, CardRank.THREE, false);

        assertFalse(card.isHidden());
    }

    @Test
    void checkHiddenCard() {
        var hiddenCard = new Card(CardSuit.DIAMONDS, CardRank.SEVEN, true);

        assertTrue(hiddenCard.isHidden());
    }

    @Test
    void checkHide() {
        var card = new Card(CardSuit.HEARTS, CardRank.ACE);
        var hiddenCard = card.hide();

        assertTrue(hiddenCard.isHidden());
        assertEquals(card.suit(), hiddenCard.suit());
        assertEquals(card.rank(), hiddenCard.rank());
    }

    @Test
    void checkUnhide() {
        var hiddenCard = new Card(CardSuit.DIAMONDS, CardRank.FIVE, true);
        var card = hiddenCard.unhide();

        assertFalse(card.isHidden());
        assertEquals(hiddenCard.suit(), card.suit());
        assertEquals(hiddenCard.rank(), card.rank());
    }

    @Test
    void checkStringRepresentation() {
        var card = new Card(CardSuit.CLUBS, CardRank.EIGHT);
        assertEquals("ВОСЬМЁРКА ТРЕФЫ", card.toString());
    }

    @Test
    void checkHiddenStringRepresentation() {
        var card = new Card(CardSuit.CLUBS, CardRank.EIGHT, true);
        assertEquals("<СКРЫТАЯ КАРТА>", card.toString());
    }

    @Test
    void checkSuit() {
        var card = new Card(CardSuit.HEARTS, CardRank.ACE);
        assertEquals(CardSuit.HEARTS, card.suit());
    }

    @Test
    void checkRank() {
        var card = new Card(CardSuit.SPADES, CardRank.QUEEN);
        assertEquals(CardRank.QUEEN, card.rank());
    }

    @Test
    void checkCost() {
        var card = new Card(CardSuit.HEARTS, CardRank.FIVE);

        assertEquals(CardRank.FIVE.getCost(), card.cost());
    }

    @Test
    void checkHiddenCardCost() {
        var hiddenCard = new Card(CardSuit.DIAMONDS, CardRank.JACK, true);

        assertEquals(0, hiddenCard.cost());
    }
}