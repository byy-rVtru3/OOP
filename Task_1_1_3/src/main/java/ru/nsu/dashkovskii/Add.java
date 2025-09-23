package ru.nsu.dashkovskii;

import java.util.Map;

/**
 * Класс, представляющий операцию сложения между двумя выражениями.
 */
public class Add extends BinaryOperations {
    public Add(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int evaluate(Map<String, Integer> variables) {
        return left.evaluate(variables) + right.evaluate(variables);
    }

    @Override
    public Expression derivative(String var) {
        return new Add(left.derivative(var), right.derivative(var));
    }

    @Override
    protected String getSymbol() {
        return "+";
    }

}
