package ru.nsu.dashkovskii;

/**
 * Абстрактный класс для бинарных операций над выражениями.
 * Содержит левый и правый операнды.
 */
public abstract class BinaryOperations extends Expression {
    protected final Expression left;
    protected final Expression right;

    public BinaryOperations(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + getSymbol() + right.toString() + ")";
    }

    protected abstract String getSymbol();
}
