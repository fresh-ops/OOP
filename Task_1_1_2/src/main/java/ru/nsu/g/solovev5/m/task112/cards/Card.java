package ru.nsu.g.solovev5.m.task112.cards;

/**
 * Represents a playing card. While a Card is immutable, it provides methods to
 * hide and unhide the card via creating a new instance.
 *
 * @param suit     a card suit
 * @param rank     a card rank for calculating the cards cost
 * @param isHidden defines whether the card is hidden
 */
public record Card(CardSuit suit, CardRank rank, boolean isHidden) {
    public Card(CardSuit suit, CardRank rank) {
        this(suit, rank, false);
    }

    /**
     * Hides this card via making a new instance with {@code true}
     * to {@link #isHidden}.
     *
     * @return a new hidden card with equal suit and rank
     */
    public Card hide() {
        return new Card(suit, rank, true);
    }

    /**
     * Unhides this card via making a new instance with {@code false}
     * to {@link #isHidden}.
     *
     * @return a new normal card with equal suit and rank
     */
    public Card unhide() {
        return new Card(suit, rank, false);
    }

    /**
     * Calculates a cost of this card.
     *
     * @return the default cost of the card of such rank if this card is not hidden,
     * 0 otherwise
     */
    public int cost() {
        if (isHidden) {
            return 0;
        }

        return rank.getCost();
    }

    @Override
    public String toString() {
        if (isHidden) {
            return "<СКРЫТАЯ КАРТА>";
        }

        return String.format("%s %s", rank, suit);
    }
}
