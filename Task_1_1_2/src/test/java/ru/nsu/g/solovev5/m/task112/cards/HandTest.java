package ru.nsu.g.solovev5.m.task112.cards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {
    Hand hand;
    Card card, ace, jack, hiddenCard;

    @BeforeEach
    void setUp() {
        hand = new Hand();
        card = new Card(CardSuit.DIAMONDS, CardRank.FIVE);
        ace = new Card(CardSuit.HEARTS, CardRank.ACE);
        jack = new Card(CardSuit.CLUBS, CardRank.JACK);
        hiddenCard = new Card(CardSuit.SPADES, CardRank.EIGHT, true);
    }

    @Test
    void checkInitState() {
        assertEquals(0, hand.getCost());
        assertEquals(0, hand.getCardsCount());
    }

    @Test
    void checkPuttingCard() {
        hand.put(card);
        hand.put(jack);

        assertEquals(card.cost() + jack.cost(), hand.getCost());
        assertEquals(2, hand.getCardsCount());
    }

    @Test
    void checkPuttingHiddenCard() {
        hand.putHidden(card);
        hand.put(hiddenCard);
        hand.putHidden(ace);

        assertEquals(0, hand.getCost());
        assertEquals(3, hand.getCardsCount());
    }

    @Test
    void checkAcesHandling() {
        hand.put(ace);
        hand.put(ace);
        hand.put(ace);
        hand.put(ace);

        assertEquals(14, hand.getCost());
    }

    @Test
    void checkHidingCard() {
        hand.put(ace);
        hand.put(jack);

        assertEquals(21, hand.getCost());

        hand.hide(0);
        assertEquals(10, hand.getCost());
    }

    @Test
    void checkUnhidingCard() {
        hand.putHidden(ace);
        hand.put(hiddenCard);

        assertEquals(0, hand.getCost());

        hand.unhide(1);
        assertEquals(hiddenCard.unhide().cost(), hand.getCost());
    }

    @Test
    void checkUnhidingAllCards() {
        hand.putHidden(ace);
        hand.put(hiddenCard);

        assertEquals(0, hand.getCost());
        assertEquals(2, hand.getCardsCount());

        hand.unhideAll();
        assertEquals(hiddenCard.unhide().cost() + ace.cost(), hand.getCost());
        assertEquals(2, hand.getCardsCount());
    }

    @Test
    void checkDiscard() {
        hand.put(ace);
        hand.put(jack);

        hand.discard();
        assertEquals(0, hand.getCost());
        assertEquals(0, hand.getCardsCount());
    }
}