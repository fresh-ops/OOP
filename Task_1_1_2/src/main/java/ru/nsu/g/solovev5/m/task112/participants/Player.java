package ru.nsu.g.solovev5.m.task112.participants;

import ru.nsu.g.solovev5.m.task112.cards.Card;
import ru.nsu.g.solovev5.m.task112.game.TextInput;

/**
 * Represents a user-controlled player.
 */
public class Player extends Participant {
    private final TextInput input;

    /**
     * Constructs a new player.
     *
     * @param name a player name
     * @param input an input provider
     */
    public Player(String name, TextInput input) {
        super(name);
        this.input = input;
    }

    @Override
    public void beforeRound(Card firstCard, Card secondCard) {
        hand.put(firstCard);
        hand.put(secondCard);
    }

    @Override
    public void beforeTurn() {

    }

    @Override
    public TurnIntent decide() {
        var decision = input.nextBooleanDecision("Взять карту?");
        if (decision) {
            return TurnIntent.TAKE_CARD;
        }
        return TurnIntent.END_TURN;
    }
}
