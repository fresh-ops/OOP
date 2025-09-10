package ru.nsu.g.solovev5.m.task112.cards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a deck of cards. Provides functionality for creating a deck,
 * shuffling and extracting cards from it.
 */
public class Deck {
    private final ArrayList<Card> cards;

    /**
     * Constructs a new deck containing specified cards.
     *
     * @param cards the array of cards to store inside the deck
     */
    public Deck(Card[] cards) {
        this.cards = new ArrayList<>();
        Collections.addAll(this.cards, cards);
    }

    /**
     * Creates and returns a new deck containing all standard playing cards.
     *
     * @return a new complete deck of cards
     */
    public static Deck full() {
        var cards = new ArrayList<Card>();

        for (var suit : CardSuit.values()) {
            for (var rank : CardRank.values()) {
                var card = new Card(suit, rank);

                cards.add(card);
            }
        }

        return new Deck(cards.toArray(new Card[0]));
    }

    /**
     * Returns {@code true} if this deck has no cards inside.
     *
     * @return {@code true} if this deck is empty
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Randomly shuffles the order of cards in this non-empty deck.
     *
     * @throws IllegalStateException if the deck is empty
     * @see #isEmpty()
     */
    public void shuffle() {
        if (isEmpty()) {
            throw new IllegalStateException("Can not shuffle an empty deck.");
        }

        Collections.shuffle(cards);
    }

    /**
     * Removes and returns a card from this deck if possible.
     *
     * @return the card removed from the deck
     * @throws IllegalStateException if the deck is empty
     * @see #isEmpty()
     */
    public Card extract() {
        if (isEmpty()) {
            throw new IllegalStateException("Can not extract card from an empty deck.");
        }

        return cards.removeLast();
    }
}
