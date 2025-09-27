package ru.nsu.g.solovev5.m.task113;

import ru.nsu.g.solovev5.m.task113.parser.Parser;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.print("Input an expression >>> ");
        var input = scanner.nextLine();
        var parser = new Parser(input);
        var expr = parser.parse();

        System.out.println(expr);
        System.out.println(expr.derivative("x"));
        System.out.println(expr.eval("x = 10; y = 13"));
        scanner.close();
    }
}