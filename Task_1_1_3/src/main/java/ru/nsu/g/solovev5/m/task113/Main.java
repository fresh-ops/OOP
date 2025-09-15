package ru.nsu.g.solovev5.m.task113;

import ru.nsu.g.solovev5.m.task113.expressions.Add;
import ru.nsu.g.solovev5.m.task113.expressions.Div;
import ru.nsu.g.solovev5.m.task113.expressions.Expression;
import ru.nsu.g.solovev5.m.task113.expressions.Mul;
import ru.nsu.g.solovev5.m.task113.expressions.Number;
import ru.nsu.g.solovev5.m.task113.expressions.Sub;
import ru.nsu.g.solovev5.m.task113.expressions.Variable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        var number = new Number(4);
        var variable = new Variable("x");
        var add = new Add(number, variable);
        var mul = new Mul(new Number(16), add);
        Expression expr = new Div(new Sub(mul, new Number(10)), new Variable("y"));

        var file = new File("test.txt");

        try (var printStream = new PrintStream(file)) {
            expr.print(printStream);
            expr.derivative("y").print(printStream);
        } catch (FileNotFoundException e) {
            System.err.println("Can not find open file to write");
        }
    }
}