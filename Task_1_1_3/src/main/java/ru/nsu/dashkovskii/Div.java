package ru.nsu.dashkovskii;

import java.util.Map;

/**
 * Класс, представляющий операцию деления между двумя выражениями.
 */
public class Div extends BinaryOperations {
    public Div(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int evaluate(Map<String, Integer> variables) {
        return left.evaluate(variables) / right.evaluate(variables);
    }

    @Override
    public Expression derivative(String var) {
        return new Div(
                new Sub(
                        new Mul(left, right.derivative(var)),
                        new Mul(left.derivative(var), right)
                ),
                new Mul(right, right)
        );
    }

    @Override
    protected String getSymbol() {
        return "/";
    }
}
