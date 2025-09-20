package ru.nsu.g.solovev5.m.task112.game;

import java.util.ArrayList;
import java.util.function.Supplier;
import ru.nsu.g.solovev5.m.task112.cards.Card;
import ru.nsu.g.solovev5.m.task112.participants.Participant;
import ru.nsu.g.solovev5.m.task112.participants.TurnIntent;

/**
 * Represents a game round.
 */
public class Round {
    private final Participant[] participants;
    private final TextDrawer drawer;
    private final Supplier<Card> cardSupplier;

    /**
     * Creates a new round.
     *
     * @param participants the array of game participants
     * @param drawer       the interface drawer
     * @param cardSupplier the card supplier
     */
    public Round(Participant[] participants, TextDrawer drawer,
                 Supplier<Card> cardSupplier) {
        this.participants = participants;
        this.drawer = drawer;
        this.cardSupplier = cardSupplier;
    }

    /**
     * Runs this round. In the end calculates results and increases winners score.
     */
    public void run() {
        for (var participant : participants) {
            beforeTurn(participant);

            if (!participant.hasBlackjack()) {
                while (participant.decide() != TurnIntent.END_TURN) {
                    drawer.takingCard(participant);
                    participant.takeCardFrom(cardSupplier);

                    drawer.participantsCards(participants);
                    if (participant.hasBust()) {
                        break;
                    }
                }
            }

            drawer.endTurn(participant);
        }

        calculateResults();
    }

    private void beforeTurn(Participant participant) {
        drawer.startTurn(participant);
        participant.beforeTurn();
    }

    private void calculateResults() {
        var blackjacked = new ArrayList<Participant>();
        var busted = new ArrayList<Participant>();
        var notBusted = new ArrayList<Participant>();
        var maxCost = 0;

        for (var participant : participants) {
            if (participant.hasBlackjack()) {
                blackjacked.add(participant);
                notBusted.add(participant);
            } else if (participant.hasBust()) {
                busted.add(participant);
            } else {
                notBusted.add(participant);
                maxCost = Math.max(maxCost, participant.getCost());
            }
        }

        if (!blackjacked.isEmpty()) {
            for (var participant : blackjacked) {
                participant.increaseScore();
                drawer.blackjackMessage(participant);
            }
        } else if (!busted.isEmpty()) {
            for (var participant : busted) {
                drawer.bustedMessage(participant);
            }
            for (var participant : notBusted) {
                participant.increaseScore();
            }
        } else {
            for (var participant : participants) {
                if (participant.getCost() == maxCost) {
                    participant.increaseScore();
                }
            }
        }
    }
}