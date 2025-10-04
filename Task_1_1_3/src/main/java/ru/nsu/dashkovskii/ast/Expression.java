package ru.nsu.dashkovskii;

import java.util.Map;
import java.util.Set;

/**
 * Абстрактный класс для математических выражений.
 * Поддерживает вычисление значения и дифференцирование.
 */
public abstract class Expression {

    public abstract int evaluate(Map<String, Integer> variables);

    public abstract Expression derivative(String var);

    /**
     * Возвращает множество всех переменных в выражении.
     */
    public abstract Set<String> getVariables();

    /**
     * Упрощает выражение согласно заданным правилам.
     */
    public Expression simplify() {
        return simplifyInternal();
    }

    protected abstract Expression simplifyInternal();

    /**
     * Проверяет, является ли выражение константой (не содержит переменных).
     */
    public boolean isConstant() {
        return getVariables().isEmpty();
    }

    /**
     * Проверяет равенство двух выражений по строковому представлению.
     */
    public boolean isEqual(Expression other) {
        return this.toString().equals(other.toString());
    }

    @Override
    public abstract String toString();

    public void print() {
        System.out.println(this.toString());
    }

    /**
     * Вычисляет выражение при заданных значениях переменных.
     * Строка assignments должна иметь вид: "x = 10; y = 13"
     */
    public int eval(String assignments) {
        Map<String, Integer> vars = Parser.parseAssignments(assignments);
        return evaluate(vars);
    }
}
