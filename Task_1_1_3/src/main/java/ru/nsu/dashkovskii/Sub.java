package ru.nsu.dashkovskii;

import java.util.Map;

public class Sub extends BinaryOperations {
    public Sub(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int evaluate(Map<String, Integer> variables) {
        return left.evaluate(variables) - right.evaluate(variables);
    }

    @Override
    public Expression derivative(String var) {
        return new Sub(left.derivative(var), right.derivative(var));
    }

    @Override
    protected String getSymbol() {
        return "-";
    }
}
