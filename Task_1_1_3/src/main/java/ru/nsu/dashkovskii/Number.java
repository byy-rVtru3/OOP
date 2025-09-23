package ru.nsu.dashkovskii;

import java.util.Map;

/**
 * Класс, представляющий числовую константу в выражении.
 */
public class Number extends Expression {
    private final int value;

    public Number(int value) {
        this.value = value;
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
    public String toString() {
        return String.valueOf(value);
    }


}
