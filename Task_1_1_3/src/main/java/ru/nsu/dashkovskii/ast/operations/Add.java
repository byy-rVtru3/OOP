package ru.nsu.dashkovskii.ast.operations;

import ru.nsu.dashkovskii.ast.Expression;
import ru.nsu.dashkovskii.ast.Number;

import java.util.HashMap;
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
    protected Expression simplifyInternal() {
        Expression leftSimplified = left.simplify();
        Expression rightSimplified = right.simplify();

        if (leftSimplified.isConstant() && rightSimplified.isConstant()) {
            int result = leftSimplified.evaluate(new HashMap<>())
                    + rightSimplified.evaluate(new HashMap<>());
            return new Number(result);
        }

        if (leftSimplified.isConstant()
                && ((Number) leftSimplified).getValue() == 0) {
            return rightSimplified;
        }
        if (rightSimplified.isConstant()
                && ((Number) rightSimplified).getValue() == 0) {
            return leftSimplified;
        }

        if (leftSimplified != left || rightSimplified != right) {
            return new Add(leftSimplified, rightSimplified);
        }

        return this;
    }

    @Override
    protected Operator getOperator() {
        return Operator.ADD;
    }
}
