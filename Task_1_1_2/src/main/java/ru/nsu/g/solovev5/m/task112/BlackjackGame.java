package ru.nsu.g.solovev5.m.task112;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import ru.nsu.g.solovev5.m.task112.cards.Card;
import ru.nsu.g.solovev5.m.task112.cards.Deck;
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

    /**
     * The programs entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        try {
            scanner = new Scanner(System.in);

            System.out.print("Добро пожаловать в Блэкджек\nПожалуйста, введите своё имя >>> ");
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
            System.out.println("Спасибо за игру");
        }
    }

    private static void game() {
        do {
            System.out.println("Раунд " + ++roundCount);
            dealCards();

            showParticipantsCards();

            round();

            System.out.println("---------------------------");
            showRoundResults();
            System.out.println("---------------------------");

            for (var participant : participants) {
                participant.discard();
            }
        } while (askContinueGame());
    }

    private static void dealCards() {
        System.out.println("Раздача карт...");

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
            System.out.println("Сейчас ходит " + participant);
            participant.beforeTurn();

            if (participant.hasBlackjack()) {
                return;
            }

            while (participant.decide() != TurnIntent.END_TURN) {
                System.out.println(participant + " берёт карту.");
                participant.takeCardFrom(BlackjackGame::extractCardSafe);

                showParticipantsCards();
                if (participant.hasBust()) {
                    return;
                }
            }

            System.out.println(participant + " закончил свой ход.");
        }
    }

    private static void showRoundResults() {
        var player = participants.get(0);
        var dealer = participants.get(1);

        System.out.println(player.hasBust());
        System.out.println(player.getCost());
        if (player.hasBust()) {
            showBustedMessage(player, dealer);
            player.increaseScore();
        } else if (dealer.hasBust()) {
            showBustedMessage(dealer, player);
            dealer.increaseScore();
        } else if (player.hasBlackjack()) {
            showBlackjackMessage(player);
            player.increaseScore();
        } else if (dealer.hasBlackjack()) {
            showBlackjackMessage(dealer);
            dealer.increaseScore();
        } else if (player.getCost() > dealer.getCost()) {
            showWinMessage(player);
            player.increaseScore();
        } else if (player.getCost() < dealer.getCost()) {
            showWinMessage(dealer);
            dealer.increaseScore();
        } else {
            System.out.println("Ничья");
        }

        System.out.println("Посмотрим на таблицу");
        for (var participant : participants) {
            System.out.printf("%s: %d\n", participant, participant.getScore());
        }
    }

    private static void showParticipantsCards() {
        System.out.println("Карты участников:");
        for (var participant : participants) {
            System.out.println("\t" + participant + ": " + participant.listCards());
        }
    }

    private static void showBustedMessage(Participant loser, Participant winner) {
        System.out.println(loser + " перебрал. побеждает " + winner);
    }

    private static void showBlackjackMessage(Participant participant) {
        System.out.println(participant + " собрал блэкджек. Поздравляем!");
    }

    private static void showWinMessage(Participant participant) {
        System.out.println("Побеждает " + participant);
    }

    private static Card extractCardSafe() {
        if (deck.isEmpty()) {
            System.out.println("Похоже колода опустела. Достаём новую.");
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
