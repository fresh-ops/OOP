package ru.nsu.g.solovev5.m.task112.participants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.g.solovev5.m.task112.cards.Card;
import ru.nsu.g.solovev5.m.task112.cards.CardRank;
import ru.nsu.g.solovev5.m.task112.cards.CardSuit;

class PlayerTest {
    Player player;
    Scanner scanner;
    Card card;
    Card ace;
    Card jack;

    @BeforeEach
    void setUp() {
        scanner = new Scanner("word 3 4 42 1 0");
        player = new Player("test", scanner);
        card = new Card(CardSuit.DIAMONDS, CardRank.SEVEN);
        ace = new Card(CardSuit.HEARTS, CardRank.ACE);
        jack = new Card(CardSuit.CLUBS, CardRank.JACK);
    }

    @Test
    void checkInitState() {
        assertEquals(0, player.getCost());
        assertEquals(0, player.getScore());
        assertFalse(player.hasBlackjack());
        assertFalse(player.hasBust());
    }

    @Test
    void checkBeforeMethods() {
        player.beforeRound(ace, jack);
        assertEquals(21, player.getCost());
        assertTrue(player.hasBlackjack());

        player.beforeTurn();
        assertEquals(21, player.getCost());
        assertTrue(player.hasBlackjack());
    }

    @Test
    void checkDecide() {
        assertEquals(TurnIntent.TAKE_CARD, player.decide());
        assertEquals(TurnIntent.END_TURN, player.decide());
    }
}