package ru.nsu.g.solovev5.m.task112.cards;

import java.util.ArrayList;

/**
 * Represents a hand of participant. Provides functionality for creating a hand,
 * adding, hiding cards and calculating total cost.
 */
public class Hand {
    public static final int MAX_ACCEPTABLE_HAND = 21;

    private final ArrayList<Card> cards;
    private final ArrayList<Integer> effectiveCosts;
    private int cost = 0;
    private int hiddenCount = 0;

    /**
     * Constructs a new empty hand.
     */
    public Hand() {
        cards = new ArrayList<>();
        effectiveCosts = new ArrayList<>();
    }

    /**
     * Calculates total cost of this hand.
     *
     * @return the sum of card costs int this hand
     */
    public int getCost() {
        if (cost == -1) {
            cost = 0;

            for (var i = 0; i < cards.size(); i++) {
                effectiveCosts.set(i, cards.get(i).cost());
                cost += effectiveCosts.get(i);
            }

            for (var i = 0; cost > MAX_ACCEPTABLE_HAND && i < cards.size(); i++) {
                if (!cards.get(i).isHidden() && cards.get(i).rank() == CardRank.ACE) {
                    effectiveCosts.set(i, 1);
                    cost -= 10;
                }
            }
        }

        return cost;
    }

    /**
     * Returns {@code true} if this hand has hidden cards, {@code false} otherwise.
     *
     * @return {@code true} if this hand has hidden cards
     */
    public boolean hasHiddenCards() {
        return hiddenCount > 0;
    }

    /**
     * Returns the cards number in this hand.
     *
     * @return the cards number
     */
    public int getCardsCount() {
        return cards.size();
    }

    /**
     * Puts the given card to this hand.
     *
     * @param card the card to add
     * @see #putHidden(Card)
     */
    public void put(Card card) {
        cards.add(card);
        effectiveCosts.add(0);

        cost = -1;
    }


    /**
     * Hides the given card and then puts it in this hand.
     *
     * @param card the card to add
     * @see #put(Card)
     */
    public void putHidden(Card card) {
        cards.add(card.hide());
        effectiveCosts.add(0);

        hiddenCount++;
    }

    /**
     * Unhides all cards in this hand.
     */
    public void unhideAll() {
        var iterator = cards.listIterator();
        var changed = false;

        while (iterator.hasNext()) {
            var card = iterator.next();

            if (card.isHidden()) {
                iterator.set(card.unhide());

                changed = true;
            }
        }

        if (changed) {
            cost = -1;
            hiddenCount = 0;
        }
    }

    /**
     * Removes all cards from this hand.
     */
    public void discard() {
        cards.clear();
        effectiveCosts.clear();

        cost = 0;
    }

    @Override
    public String toString() {
        getCost();
        var labels = new ArrayList<String>();

        for (var i = 0; i < cards.size(); i++) {
            var card = cards.get(i);
            var label = card.toString();

            if (!card.isHidden()) {
                label = String.format("%s (%d)", label, effectiveCosts.get(i));
            }

            labels.add(label);
        }

        if (hasHiddenCards()) {
            return labels.toString();
        }
        return String.format("%s => %d", labels, cost);
    }
}
