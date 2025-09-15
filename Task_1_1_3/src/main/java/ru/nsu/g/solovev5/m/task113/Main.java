package ru.nsu.g.solovev5.m.task113;

import ru.nsu.g.solovev5.m.task113.expressions.Add;
import ru.nsu.g.solovev5.m.task113.expressions.Div;
import ru.nsu.g.solovev5.m.task113.expressions.Expression;
import ru.nsu.g.solovev5.m.task113.expressions.Mul;
import ru.nsu.g.solovev5.m.task113.expressions.Number;
import ru.nsu.g.solovev5.m.task113.expressions.Sub;
import ru.nsu.g.solovev5.m.task113.expressions.Variable;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        var number = new Number(4);
        var variable = new Variable("x");
        var add = new Add(number, variable);
        var mul = new Mul(new Number(16), add);
        Expression expr = new Div(new Sub(mul, new Number(10)), new Variable("y"));

        expr.print(System.out);
    }
}