package ru.nsu.dashkovskii.ast.operations;

import java.util.HashMap;
import java.util.Map;
import ru.nsu.dashkovskii.ast.Expression;
import ru.nsu.dashkovskii.ast.Number;

/**
 * Класс, представляющий операцию вычитания между двумя выражениями.
 */
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
    protected Expression simplifyInternal() {
        Expression leftSimplified = left.simplify();
        Expression rightSimplified = right.simplify();

        if (leftSimplified.isConstant() && rightSimplified.isConstant()) {
            int result = leftSimplified.evaluate(new HashMap<>())
                    - rightSimplified.evaluate(new HashMap<>());
            return new Number(result);
        }

        if (rightSimplified.isConstant()
                && ((Number) rightSimplified).getValue() == 0) {
            return leftSimplified;
        }

        if (leftSimplified.isEqual(rightSimplified)) {
            return new Number(0);
        }

        if (leftSimplified != left || rightSimplified != right) {
            return new Sub(leftSimplified, rightSimplified);
        }

        return this;
    }

    @Override
    protected Operator getOperator() {
        return Operator.SUB;
    }
}
