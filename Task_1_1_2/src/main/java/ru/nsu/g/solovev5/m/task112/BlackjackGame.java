package ru.nsu.g.solovev5.m.task112;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import ru.nsu.g.solovev5.m.task112.cards.Card;
import ru.nsu.g.solovev5.m.task112.cards.Deck;
import ru.nsu.g.solovev5.m.task112.game.Round;
import ru.nsu.g.solovev5.m.task112.game.TextDrawer;
import ru.nsu.g.solovev5.m.task112.game.TextInput;
import ru.nsu.g.solovev5.m.task112.participants.Dealer;
import ru.nsu.g.solovev5.m.task112.participants.Participant;
import ru.nsu.g.solovev5.m.task112.participants.Player;
import ru.nsu.g.solovev5.m.task112.participants.TurnIntent;

/**
 * A main game class.
 */
public class BlackjackGame {
    private static ArrayList<Participant> participants;
    private static Deck deck;
    private static int roundCount;
    private static TextInput input;
    private static TextDrawer drawer;

    /**
     * The programs entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        try {
            drawer = new TextDrawer(System.out);
            input = new TextInput(System.in, System.out);

            drawer.welcome();
            var name = input.nextLine("Пожалуйста, введите своё имя");

            participants = new ArrayList<>();
            participants.add(new Player(name, input));
            participants.add(new Dealer());

            deck = Deck.full();
            roundCount = 0;

            game();
            input.close();
        } catch (NoSuchElementException e) {
            System.out.println("Внезапное завершение потока ввода");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Непредвиденное исключение");
        } finally {
            drawer.thanks();
        }
    }

    private static void game() {
        do {
            drawer.startRound(++roundCount);
            dealCards();

            drawer.participantsCards(participants.toArray(new Participant[0]));

            var round = new Round(roundCount,
                    participants.toArray(new Participant[0]),
                    drawer, BlackjackGame::extractCardSafe
            );

            round.run();

            drawer.separator();
            drawer.scoreTable(participants.toArray(new Participant[0]));
            drawer.separator();

            for (var participant : participants) {
                participant.discard();
            }
        } while (askContinueGame());
    }

    private static void dealCards() {
        drawer.dealingCards();

        for (var participant : participants) {
            var firstCard = extractCardSafe();
            var secondCard = extractCardSafe();
            participant.beforeRound(firstCard, secondCard);
        }
    }

    private static Card extractCardSafe() {
        if (deck.isEmpty()) {
            drawer.emptyDeck();
            deck = Deck.full();
            deck.shuffle();
        }

        return deck.extract();
    }

    private static boolean askContinueGame() {
        return input.nextBooleanDecision("Сыграть ещё раунд?");
    }
}
