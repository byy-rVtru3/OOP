package ru.nsu.dashkovskii.ast.operations;

import java.util.HashMap;
import java.util.Map;
import ru.nsu.dashkovskii.ast.Expression;
import ru.nsu.dashkovskii.ast.Number;

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
            int result = leftSimplified.evaluate(new HashMap<>())
                    * rightSimplified.evaluate(new HashMap<>());
            return new Number(result);
        }

        if ((leftSimplified.isConstant()
                && ((Number) leftSimplified).getValue() == 0)
                || (rightSimplified.isConstant()
                && ((Number) rightSimplified).getValue() == 0)) {
            return new Number(0);
        }

        if (leftSimplified.isConstant()
                && ((Number) leftSimplified).getValue() == 1) {
            return rightSimplified;
        }
        if (rightSimplified.isConstant()
                && ((Number) rightSimplified).getValue() == 1) {
            return leftSimplified;
        }

        if (leftSimplified != left || rightSimplified != right) {
            return new Mul(leftSimplified, rightSimplified);
        }

        return this;
    }

    @Override
    protected Operator getOperator() {
        return Operator.MUL;
    }
}
