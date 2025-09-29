package ru.nsu.g.solovev5.m.task112.game;

import java.io.PrintStream;
import ru.nsu.g.solovev5.m.task112.participants.Participant;

/**
 * Represents a text user interface. Provides methods to output data to
 * the given print stream.
 */
public class TextDrawer {
    private final PrintStream stream;

    /**
     * Constructs a new TextDrawer.
     *
     * @param stream a stream to output data
     */
    public TextDrawer(PrintStream stream) {
        this.stream = stream;
    }

    /**
     * Shows the welcome message.
     */
    public void welcome() {
        stream.println("Добро пожаловать в Блэкджек");
    }

    /**
     * Shows the round intro.
     *
     * @param roundNumber the number of the round to introduce
     */
    public void startRound(int roundNumber) {
        stream.printf("Раунд %d\n", roundNumber);
    }

    /**
     * Notifies the cards dealing.
     */
    public void dealingCards() {
        stream.println("Раздача карт...");
    }

    /**
     * Shows the turn intro.
     *
     * @param participant the participant making a turn
     */
    public void startTurn(Participant participant) {
        stream.printf("Сейчас ходит %s\n", participant);
    }

    /**
     * Notifies when the participant takes a card.
     *
     * @param participant the participant taking a card
     */
    public void takingCard(Participant participant) {
        stream.printf("%s берёт карту\n", participant);
    }

    /**
     * Shows the turn outro.
     *
     * @param participant the participant making a turn
     */
    public void endTurn(Participant participant) {
        stream.printf("%s закончил свой ход\n", participant);
    }

    /**
     * Shows the table with participants cards.
     *
     * @param participants the array of participants
     */
    public void participantsCards(Participant[] participants) {
        stream.println("Карты участников:");

        for (var participant : participants) {
            stream.printf("\t%s: %s\n", participant, participant.listCards());
        }
    }

    /**
     * Shows the message about a blackjack in the game.
     *
     * @param blackjacked the participant who has a blackjack
     */
    public void blackjackMessage(Participant blackjacked) {
        stream.printf("%s собрал блэкджек. Поздравляем!\n", blackjacked);
    }

    /**
     * Shows the message about a bust in the game.
     *
     * @param busted the participant who has a bust
     */
    public void bustedMessage(Participant busted) {
        stream.printf("Похоже, что %s перебрал\n", busted);
    }

    /**
     * Shows the message about winning.
     *
     * @param winner the winner of this game
     */
    public void winMessage(Participant winner) {
        stream.printf("Побеждает %s\n", winner);
    }

    /**
     * Shows the message about a drow.
     */
    public void draw() {
        stream.println("В этом раунде никто не выиграл");
    }

    /**
     * Shows the participants scores table.
     *
     * @param participants the array of participants
     */
    public void scoreTable(Participant[] participants) {
        stream.println("Посмотрим на таблицу:");

        for (var participant : participants) {
            stream.printf("\t%s: %d\n", participant, participant.getScore());
        }
    }

    /**
     * Notifies about an empty deck.
     */
    public void emptyDeck() {
        stream.println("Похоже, что колода опустела. Достаём новую");
    }

    /**
     * Thanks the user for playing.
     */
    public void thanks() {
        stream.println("Спасибо за игру");
    }

    /**
     * Shows the separator.
     */
    public void separator() {
        stream.println("------------------------------------------");
    }
}
