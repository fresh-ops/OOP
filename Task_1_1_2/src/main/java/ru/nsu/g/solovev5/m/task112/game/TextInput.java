package ru.nsu.g.solovev5.m.task112.game;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Represents a text user input. Provides methods to ask user for entering
 * a line or making a decision.
 */
public class TextInput {
    private final Scanner scanner;
    private final PrintStream printer;

    /**
     * Constructs a new TextInput.
     *
     * @param input a stream to input data
     * @param output a stream to output data
     */
    public TextInput(InputStream input, PrintStream output) {
        scanner = new Scanner(input);
        printer = output;
    }

    /**
     * Asks user for entering a line.
     *
     * @param message the message to show before input request
     * @return entered string
     */
    public String nextLine(String message) {
        printer.printf("%s >>> ", message);
        return scanner.nextLine();
    }

    /**
     * Asks user for making decision between two options.
     *
     * @param message the message to show before input request
     * @return {@code true} if user accepts option, {@code false} otherwise
     */
    public boolean nextBooleanDecision(String message) {
        while (true) {
            printer.printf("%s (1/0) >>> ", message);
            if (!scanner.hasNext()) {
                break;
            }

            try {
                var input = scanner.nextInt();

                if (input == 0 || input == 1) {
                    return input == 1;
                }

                printer.println("Недоступная опция");
            } catch (Exception e) {
                printer.print("""
                        Пожалуйста, введите одну цифру:
                        \t1, чтобы подтвердить
                        \t0, чтобы отказаться
                        """);
                scanner.next();
            }
        }
        return false;
    }

    /**
     * Closes input stream.
     */
    public void close() {
        scanner.close();
    }
}
