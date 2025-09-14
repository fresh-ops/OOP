package ru.nsu.g.solovev5.m.task112.participants;

import ru.nsu.g.solovev5.m.task112.cards.Card;

public class Dealer extends Participant {
    public Dealer() {
        super("Диллер");
    }

    @Override
    public void beforeRound(Card firstCard, Card secondCard) {
        hand.put(firstCard);
        hand.putHidden(secondCard);
    }

    @Override
    public void beforeTurn() {
        System.out.println(this + " открывает свои карты.");
        hand.unhideAll();
        System.out.println("\t" + this + ": " + listCards());
    }

    @Override
    public TurnIntent decide() {
        if (hand.getCost() < 17) {
            return TurnIntent.TAKE_CARD;
        }

        return TurnIntent.END_TURN;
    }
}
