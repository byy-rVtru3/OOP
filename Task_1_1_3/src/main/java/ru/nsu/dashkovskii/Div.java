package ru.nsu.dashkovskii;

import java.util.Map;
import java.util.HashMap;

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
                        new Mul(left.derivative(var), right),
                        new Mul(left, right.derivative(var))
                ),
                new Mul(right, right)
        );
    }

    @Override
    protected Expression simplifyInternal() {
        Expression leftSimplified = left.simplify();
        Expression rightSimplified = right.simplify();

        if (leftSimplified.isConstant() && rightSimplified.isConstant()) {
            try {
                int rightValue = rightSimplified.evaluate(new HashMap<>());
                if (rightValue != 0) {
                    int result = leftSimplified.evaluate(new HashMap<>()) / rightValue;
                    return new Number(result);
                }
            } catch (Exception e) {
                // Если не удалось вычислить, возвращаем упрощенное выражение
            }
        }

        if (leftSimplified instanceof Number && ((Number) leftSimplified).getValue() == 0) {
            return new Number(0);
        }

        if (rightSimplified instanceof Number && ((Number) rightSimplified).getValue() == 1) {
            return leftSimplified;
        }

        if (leftSimplified.isEqual(rightSimplified)) {
            return new Number(1);
        }

        if (leftSimplified != left || rightSimplified != right) {
            return new Div(leftSimplified, rightSimplified);
        }

        return this;
    }

    @Override
    protected String getSymbol() {
        return "/";
    }
}
