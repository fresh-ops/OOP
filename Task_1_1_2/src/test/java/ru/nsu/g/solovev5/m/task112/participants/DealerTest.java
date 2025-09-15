package ru.nsu.g.solovev5.m.task112.participants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task112.cards.Card;
import ru.nsu.g.solovev5.m.task112.cards.CardRank;
import ru.nsu.g.solovev5.m.task112.cards.CardSuit;

class DealerTest {
    Dealer dealer;
    Card card;
    Card ace;
    Card jack;

    @BeforeEach
    void setUp() {
        dealer = new Dealer();
        card = new Card(CardSuit.DIAMONDS, CardRank.SEVEN);
        ace = new Card(CardSuit.HEARTS, CardRank.ACE);
        jack = new Card(CardSuit.CLUBS, CardRank.JACK);
    }

    @Test
    void checkInitState() {
        assertEquals(0, dealer.getCost());
        assertEquals(0, dealer.getScore());
        assertFalse(dealer.hasBlackjack());
        assertFalse(dealer.hasBust());
    }

    @Test
    void checkBlackjack() {
        dealer.takeCardFrom(() -> ace);
        assertFalse(dealer.hasBlackjack());

        dealer.takeCardFrom(() -> jack);
        assertTrue(dealer.hasBlackjack());
        assertEquals(21, dealer.getCost());

        dealer.takeCardFrom(() -> jack);
        assertFalse(dealer.hasBlackjack());
        assertEquals(21, dealer.getCost());
    }

    @Test
    void checkBust() {
        dealer.takeCardFrom(() -> jack);
        dealer.takeCardFrom(() -> jack);
        assertFalse(dealer.hasBust());

        dealer.takeCardFrom(() -> jack);
        assertTrue(dealer.hasBust());
    }

    @Test
    void checkScore() {
        assertEquals(0, dealer.getScore());

        dealer.increaseScore();
        assertEquals(1, dealer.getScore());

        dealer.increaseScore();
        assertEquals(2, dealer.getScore());

        dealer.increaseScore();
        assertEquals(3, dealer.getScore());
    }

    @Test
    void checkDecide() {
        assertEquals(TurnIntent.TAKE_CARD, dealer.decide());

        dealer.takeCardFrom(() -> jack);
        assertEquals(TurnIntent.TAKE_CARD, dealer.decide());

        dealer.takeCardFrom(() -> card);
        assertEquals(TurnIntent.END_TURN, dealer.decide());
    }

    @Test
    void checkName() {
        assertEquals("Диллер", dealer.toString());
    }
}