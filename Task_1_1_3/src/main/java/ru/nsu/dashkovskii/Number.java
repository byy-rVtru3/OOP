package ru.nsu.dashkovskii;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * Класс, представляющий числовую константу в выражении.
 */
public class Number extends Expression {
    private final int value;

    public Number(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int evaluate(Map<String, Integer> variables) {
        return value;
    }

    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    @Override
    public Set<String> getVariables() {
        return new HashSet<>();
    }

    @Override
    protected Expression simplifyInternal() {
        return this; // Константы уже упрощены
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
