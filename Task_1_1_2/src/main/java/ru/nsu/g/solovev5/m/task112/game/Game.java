package ru.nsu.g.solovev5.m.task112.game;

import ru.nsu.g.solovev5.m.task112.cards.Card;
import ru.nsu.g.solovev5.m.task112.cards.Deck;
import ru.nsu.g.solovev5.m.task112.participants.Participant;

/**
 * Represents a game.
 */
public class Game {
    private final Participant[] participants;
    private final TextDrawer drawer;
    private final TextInput input;
    private Deck deck;
    private int roundCount;

    /**
     * Constructs a new game.
     *
     * @param participants the participants of a new game
     * @param drawer the drawer to show interface
     * @param input the input provider
     */
    public Game(Participant[] participants, TextDrawer drawer, TextInput input) {
        this.participants = participants;
        this.drawer = drawer;
        this.input = input;

        deck = Deck.full();
        roundCount = 1;
    }

    /**
     * Runs this game.
     */
    public void run() {
        do {
            drawer.startRound(roundCount++);
            dealCards();
            drawer.participantsCards(participants);

            new Round(participants, drawer, this::extractCardSafe).run();

            showScores();
            discardCards();
        } while (askContinueGame());
    }

    private Card extractCardSafe() {
        if (deck.isEmpty()) {
            deck = Deck.full();
            deck.shuffle();
        }

        return deck.extract();
    }

    private void dealCards() {
        drawer.dealingCards();

        if (!deck.isEmpty()) {
            deck.shuffle();
        }

        for (var participant : participants) {
            var firstCard = extractCardSafe();
            var secondCard = extractCardSafe();

            participant.beforeRound(firstCard, secondCard);
        }
    }

    private void discardCards() {
        for (var participant : participants) {
            participant.discard();
        }
    }

    private void showScores() {
        drawer.separator();
        drawer.scoreTable(participants);
        drawer.separator();
    }

    private boolean askContinueGame() {
        return input.nextBooleanDecision("Сыграть ещё раунд?");
    }
}
