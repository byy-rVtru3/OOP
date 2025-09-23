package ru.nsu.dashkovskii;

import java.util.Map;

/**
 * Класс, представляющий операцию умножения между двумя выражениями.
 */
public class Mul extends BinaryOperations {

    public Mul(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int evaluate(Map<String, Integer> variables) {
        return left.evaluate(variables) * right.evaluate(variables);
    }

    @Override
    public Expression derivative(String var) {
        return new Add(
                new Mul(left.derivative(var), right),
                new Mul(left, right.derivative(var))
        );
    }

    @Override
    protected String getSymbol() {
        return "*";
    }
}
