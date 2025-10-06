package ru.nsu.dashkovskii.ast.operations;

import ru.nsu.dashkovskii.ast.Expression;

import java.util.HashSet;
import java.util.Set;

/**
 * Абстрактный класс для бинарных операций над выражениями.
 * Содержит левый и правый операнды.
 */
public abstract class BinaryOperations extends Expression {
    protected final Expression left;
    protected final Expression right;
    private final Set<String> variables;

    public BinaryOperations(Expression left, Expression right) {
        this.left = left;
        this.right = right;
        this.variables = new HashSet<>();
        this.variables.addAll(left.getVariables());
        this.variables.addAll(right.getVariables());
    }

    @Override
    public Set<String> getVariables() {
        return variables;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + getOperator().getSymbol() + right.toString() + ")";
    }

    protected abstract Operator getOperator();
}
