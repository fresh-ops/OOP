package ru.nsu.g.solovev5.m.task113.parser;

import ru.nsu.g.solovev5.m.task113.expressions.Add;
import ru.nsu.g.solovev5.m.task113.expressions.Div;
import ru.nsu.g.solovev5.m.task113.expressions.Expression;
import ru.nsu.g.solovev5.m.task113.expressions.Mul;
import ru.nsu.g.solovev5.m.task113.expressions.Number;
import ru.nsu.g.solovev5.m.task113.expressions.Sub;
import ru.nsu.g.solovev5.m.task113.expressions.Variable;

/**
 * A parser of arithmetic expressions. Creates an arithmetic tree
 * out of an input string.
 */
public class Parser {
    private final Lexer lexer;

    /**
     * Constructs a new parser.
     *
     * @param input a string containing arithmetic expression
     */
    public Parser(String input) {
        this.lexer = new Lexer(input);
    }

    /**
     * Parses the string inside this parser.
     *
     * @return an expression tree
     */
    public Expression parse() {
        var result = precedencedExpression(0);

        if (lexer.current().type() != TokenType.EOF) {
            throw new RuntimeException(
                "Unexpected characters after expression: " +
                lexer.current().value()
            );
        }

        return result;
    }

    private Expression precedencedExpression(int minPrecedence) {
        var result = parenthesisedOrAtom();

        while (true) {
            var current = lexer.current();
            if (current.type() != TokenType.OPERATOR) {
                break;
            }

            var operator = current.value();
            var precedence = getPrecedence(operator);

            if (precedence < minPrecedence) {
                break;
            }

            lexer.next();
            var right = precedencedExpression(precedence + 1);
            result = createOperation(operator, result, right);
        }

        return result;
    }

    private Expression parenthesisedOrAtom() {
        if (lexer.current().type() == TokenType.LEFT_PARENTHESIS) {
            return parenthesised();
        } else {
            var expr = atom();

            if (expr == null) {
                throw new RuntimeException("Unexpected token \"" +
                    lexer.current().value() +
                    "\". Expected number, variable or \"(\""
                );
            }

            return expr;
        }
    }

    private Expression parenthesised() {
        lexer.next();
        var expr = precedencedExpression(0);

        if (lexer.current().type() == TokenType.EOF) {
            throw new RuntimeException(
                "Unexpected end of input, expected \")\""
            );
        }
        if (lexer.current().type() != TokenType.RIGHT_PARENTHESIS) {
            throw new RuntimeException(
                "Expected \")\", got " +
                lexer.current().type()
            );
        }

        lexer.next();
        return expr;
    }

    private Expression atom() {
        var result = switch (lexer.current().type()) {
            case NUMBER -> number();
            case VARIABLE -> variable();
            default -> null;
        };

        if (result != null) {
            lexer.next();
        }

        return result;
    }

    private Expression number() {
        var value = Integer.parseInt(lexer.current().value());
        return new Number(value);
    }

    private Expression variable() {
        return new Variable(lexer.current().value());
    }

    private int getPrecedence(String operator) {
        return switch (operator) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> 0;
        };
    }

    private Expression createOperation(String operator, Expression left, Expression right) {
        return switch (operator) {
            case "+" -> new Add(left, right);
            case "-" -> new Sub(left, right);
            case "*" -> new Mul(left, right);
            case "/" -> new Div(left, right);
            default -> throw new RuntimeException(
                "Usupported operator \"" +
                operator +
                "\""
            );
        };
    }
}
