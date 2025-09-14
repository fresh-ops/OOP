package ru.nsu.g.solovev5.m.task112.participants;

import ru.nsu.g.solovev5.m.task112.cards.Card;

import java.util.Scanner;

public class Player extends Participant {
    private final Scanner scanner;

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
                }

                System.out.println("Недоступная опция");
            } catch (Exception e) {
                System.out.println("Пожалуйста, введите одну цифру: \n" +
                        "\t1, чтобы взять карту\n" +
                        "\t0, чтобы закончить ход");
                scanner.next();
            }
        }
    }
}
