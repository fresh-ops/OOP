package ru.nsu.g.solovev5.m.task112.cards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void checkEmptyDeck() {
        var deck = new Deck(new Card[]{});

        assertTrue(deck.isEmpty());
    }

    @Test
    void checkNonEmptyDeck() {
        var deck = new Deck(new Card[]{new Card(CardSuit.SPADES, CardRank.QUEEN)});

        assertFalse(deck.isEmpty());
    }

    @Test
    void checkEmptinessAfterExtracting() {
        var deck = new Deck(new Card[]{new Card(CardSuit.SPADES, CardRank.QUEEN)});
        assertFalse(deck.isEmpty());

        deck.extract();
        assertTrue(deck.isEmpty());
    }

    @Test
    void checkFullDeck() {
        var deck = Deck.full();

        assertFalse(deck.isEmpty());
    }

    @Test
    void checkExtracting() {
        var deck = new Deck(new Card[]{new Card(CardSuit.SPADES, CardRank.QUEEN)});
        var extractedCard = deck.extract();

        assertEquals(new Card(CardSuit.SPADES, CardRank.QUEEN), extractedCard);
    }

    @Test
    void checkExtractingFromEmptyDeck() {
        var deck = new Deck(new Card[]{});

        assertThrows(IllegalStateException.class, deck::extract);
    }

    @Test
    void checkExtractingFromEqualDeck() {
        var cards = new Card[]{
                new Card(CardSuit.SPADES, CardRank.EIGHT),
                new Card(CardSuit.CLUBS, CardRank.ACE)
        };
        var deck = new Deck(cards);
        var anotherDeck = new Deck(cards);

        assertEquals(deck.extract(), anotherDeck.extract());
        assertArrayEquals(new Card[]{
                    new Card(CardSuit.SPADES, CardRank.EIGHT),
                    new Card(CardSuit.CLUBS, CardRank.ACE)
                },
                cards);

    }

    @Test
    void checkShuffling() {
        var deck = Deck.full();
        var anotherDeck = Deck.full();

        deck.shuffle();
        assertNotEquals(anotherDeck.extract(), deck.extract());
    }

    @Test
    void checkShufflingEmptyDeck() {
        var deck = new Deck(new Card[]{});

        assertThrows(IllegalStateException.class, deck::shuffle);
    }
}