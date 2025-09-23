package ru.nsu.dashkovskii;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * Класс, представляющий переменную в математическом выражении.
 */
public class Variable extends Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public int evaluate(Map<String, Integer> variables) {
        if (!variables.containsKey(name)) {
            throw new IllegalArgumentException("Нет значения для переменной " + name);
        }
        return variables.get(name);
    }

    @Override
    public Expression derivative(String var) {
        if (name.equals(var)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    @Override
    public Set<String> getVariables() {
        Set<String> vars = new HashSet<>();
        vars.add(name);
        return vars;
    }

    @Override
    protected Expression simplifyInternal() {
        return this; // Переменные не упрощаются
    }

    @Override
    public String toString() {
        return name;
    }
}
