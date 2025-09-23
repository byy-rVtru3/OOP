package ru.nsu.dashkovskii;

import java.util.Map;
import java.util.HashMap;

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
    protected Expression simplifyInternal() {
        Expression leftSimplified = left.simplify();
        Expression rightSimplified = right.simplify();

        if (leftSimplified.isConstant() && rightSimplified.isConstant()) {
            try {
                int result = leftSimplified.evaluate(new HashMap<>()) + rightSimplified.evaluate(new HashMap<>());
                return new Number(result);
            } catch (Exception e) {
                // Если не удалось вычислить, возвращаем упрощенное выражение
            }
        }

        if (leftSimplified instanceof Number && ((Number) leftSimplified).getValue() == 0) {
            return rightSimplified;
        }
        if (rightSimplified instanceof Number && ((Number) rightSimplified).getValue() == 0) {
            return leftSimplified;
        }

        if (leftSimplified != left || rightSimplified != right) {
            return new Add(leftSimplified, rightSimplified);
        }

        return this;
    }

    @Override
    protected String getSymbol() {
        return "+";
    }
}
