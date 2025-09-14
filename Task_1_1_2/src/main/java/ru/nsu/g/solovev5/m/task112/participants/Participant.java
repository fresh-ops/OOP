package ru.nsu.g.solovev5.m.task112.participants;

import ru.nsu.g.solovev5.m.task112.cards.Card;
import ru.nsu.g.solovev5.m.task112.cards.Hand;

import java.util.function.Supplier;

/**
 * Abstract class represents a participant. Provides functionality for interacting with cards,
 * managing wins count.
 */
public abstract class Participant {
    protected final Hand hand;
    private final String name;
    private int score;

    public Participant(String name) {
        hand = new Hand();
        this.name = name;
        score = 0;
    }

    /**
     * Prepares this participant for the next round.
     *
     * @param firstCard  the first card given to this participant
     * @param secondCard the second card given to this participant
     */
    public abstract void beforeRound(Card firstCard, Card secondCard);

    /**
     * Prepares this participant for next turn.
     */
    public abstract void beforeTurn();

    /**
     * Increases the score of this participant.
     */
    public void increaseScore() {
        score++;
    }

    /**
     * Returns the score of this participant.
     *
     * @return the score of this participant
     */
    public int getScore() {
        return score;
    }

    /**
     * Makes a string representation of this participant cards.
     *
     * @return a string representation of hand
     */
    public String listCards() {
        return hand.toString();
    }

    /**
     * Removes all cards from this participants hand.
     */
    public void discard() {
        hand.discard();
    }

    /**
     * Takes a card from given supplier and puts it in the hand.
     *
     * @param cardSupplier a card supplier
     */
    public void takeCardFrom(Supplier<Card> cardSupplier) {
        var card = cardSupplier.get();

        hand.put(card);
    }

    /**
     * Returns the cost of cards in this participants hand.
     *
     * @return the cost of cards in this participants hand
     */
    public int getCost() {
        return hand.getCost();
    }

    /**
     * Returns {@code true} if this participant has a blackjack, {@code false} otherwise.
     *
     * @return {@code true} if this participant has a blackjack
     */
    public boolean hasBlackjack() {
        return hand.getCost() == 21 && hand.getCardsCount() == 2;
    }

    /**
     * Returns {@code true} if this participant has a bust, {@code false} otherwise.
     *
     * @return {@code true} if this participant has a bust
     */
    public boolean hasBust() {
        return hand.getCost() > 21;
    }

    /**
     * Returns this participants decision about next move.
     *
     * @return this participants decision
     */
    public abstract TurnIntent decide();

    @Override
    public String toString() {
        return name;
    }
}
