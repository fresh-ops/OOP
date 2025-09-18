package ru.nsu.g.solovev5.m.task112;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import ru.nsu.g.solovev5.m.task112.cards.Card;
import ru.nsu.g.solovev5.m.task112.cards.Deck;
import ru.nsu.g.solovev5.m.task112.game.TextDrawer;
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
    private static Scanner scanner;
    private static TextDrawer drawer;

    /**
     * The programs entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        try {
            scanner = new Scanner(System.in);
            drawer = new TextDrawer(System.out);

            drawer.welcome();
            System.out.print("Пожалуйста, введите своё имя >>> ");
            var name = scanner.next();

            participants = new ArrayList<>();
            participants.add(new Player(name, scanner));
            participants.add(new Dealer());

            deck = Deck.full();
            roundCount = 0;

            game();
            scanner.close();
        } catch (NoSuchElementException e) {
            System.out.println("Внезапное завершение потока ввода");
        } catch (Exception e) {
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

            round();

            drawer.separator();
            showRoundResults();
            drawer.separator();

            for (var participant : participants) {
                participant.discard();
            }
        } while (askContinueGame());
    }

    private static void dealCards() {
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

    private static void round() {
        for (var participant : participants) {
            drawer.startTurn(participant);
            participant.beforeTurn();

            if (participant.hasBlackjack()) {
                return;
            }

            while (participant.decide() != TurnIntent.END_TURN) {
                drawer.takingCard(participant);
                participant.takeCardFrom(BlackjackGame::extractCardSafe);

                drawer.participantsCards(participants.toArray(new Participant[0]));
                if (participant.hasBust()) {
                    return;
                }
            }

            drawer.endTurn(participant);
        }
    }

    private static void showRoundResults() {
        var player = participants.get(0);
        var dealer = participants.get(1);

        if (player.hasBust()) {
            drawer.bustedMessage(player);
            player.increaseScore();
        } else if (dealer.hasBust()) {
            drawer.bustedMessage(dealer);
            dealer.increaseScore();
        } else if (player.hasBlackjack()) {
            drawer.blackjackMessage(player);
            player.increaseScore();
        } else if (dealer.hasBlackjack()) {
            drawer.blackjackMessage(dealer);
            dealer.increaseScore();
        } else if (player.getCost() > dealer.getCost()) {
            drawer.winMessage(player);
            player.increaseScore();
        } else if (player.getCost() < dealer.getCost()) {
            drawer.winMessage(dealer);
            dealer.increaseScore();
        } else {
            drawer.draw();
        }

        drawer.scoreTable(participants.toArray(new Participant[0]));
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
        while (true) {
            System.out.print("Сыграть ещё раунд? (1/0) >>> ");
            try {
                var input = scanner.nextInt();

                switch (input) {
                    case 1:
                        return true;
                    case 0:
                        return false;
                    default:
                        break;
                }

                System.out.println("Недоступная опция");
            } catch (Exception e) {
                System.out.println("""
                        Пожалуйста, введите одну цифру:
                        \t1, чтобы продолжить игру
                        \t0, чтобы завершить игру""");
                scanner.next();
            }
        }
    }
}
