package ru.nsu.g.solovev5.m.task113;

import java.util.Scanner;
import ru.nsu.g.solovev5.m.task113.parser.Parser;

/**
 * A main class.
 */
public class Main {
    /**
     * Entry point of the program.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.print("Input an expression >>> ");
        var input = scanner.nextLine();
        var parser = new Parser(input);
        var expr = parser.parse();

        System.out.println(expr);
        System.out.println(expr.simplify());
        scanner.close();
    }
}