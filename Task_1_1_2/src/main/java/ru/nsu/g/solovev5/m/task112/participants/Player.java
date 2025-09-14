package ru.nsu.g.solovev5.m.task112.participants;

import java.util.Scanner;
import ru.nsu.g.solovev5.m.task112.cards.Card;

/**
 * Represents a user-controlled player.
 */
public class Player extends Participant {
    private final Scanner scanner;

    /**
     * Constructs a new player.
     *
     * @param name a player name
     * @param scanner a scanner to provide input
     */
    public Player(String name, Scanner scanner) {
        super(name);
        this.scanner = scanner;
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
        while (true) {
            System.out.print("Взять карту? (1/0) >>> ");
            try {
                var input = scanner.nextInt();

                switch (input) {
                    case 1:
                        return TurnIntent.TAKE_CARD;
                    case 0:
                        return TurnIntent.END_TURN;
                    default:
                        break;
                }

                System.out.println("Недоступная опция");
            } catch (Exception e) {
                System.out.println("""
                        Пожалуйста, введите одну цифру:
                        \t1, чтобы взять карту
                        \t0, чтобы закончить ход""");
                scanner.next();
            }
        }
    }
}
