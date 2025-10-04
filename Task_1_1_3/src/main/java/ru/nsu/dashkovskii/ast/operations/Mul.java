package ru.nsu.dashkovskii;

import java.util.HashMap;
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
    protected Expression simplifyInternal() {
        Expression leftSimplified = left.simplify();
        Expression rightSimplified = right.simplify();

        if (leftSimplified.isConstant() && rightSimplified.isConstant()) {
            try {
                int result = leftSimplified.evaluate(new HashMap<>())
                        * rightSimplified.evaluate(new HashMap<>());
                return new Number(result);
            } catch (Exception e) {
                // Если не удалось вычислить, возвращаем упрощенное выражение
            }
        }

        if ((leftSimplified instanceof Number
                && ((Number) leftSimplified).getValue() == 0)
                || (rightSimplified instanceof Number
                && ((Number) rightSimplified).getValue() == 0)) {
            return new Number(0);
        }

        if (leftSimplified instanceof Number
                && ((Number) leftSimplified).getValue() == 1) {
            return rightSimplified;
        }
        if (rightSimplified instanceof Number
                && ((Number) rightSimplified).getValue() == 1) {
            return leftSimplified;
        }

        if (leftSimplified != left || rightSimplified != right) {
            return new Mul(leftSimplified, rightSimplified);
        }

        return this;
    }

    @Override
    protected String getSymbol() {
        return "*";
    }
}
