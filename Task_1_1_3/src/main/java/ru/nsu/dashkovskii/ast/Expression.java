package ru.nsu.dashkovskii;

import java.util.Map;
import java.util.Set;

/**
 * Абстрактный класс для математических выражений.
 * Поддерживает вычисление значения и дифференцирование.
 */
public abstract class Expression {

    /**
     * Вычисляет значение выражения при заданных значениях переменных.
     *
     * @param variables карта переменных и их значений
     * @return результат вычисления выражения
     */
    public abstract int evaluate(Map<String, Integer> variables);

    /**
     * Вычисляет производную выражения по указанной переменной.
     *
     * @param var имя переменной, по которой берётся производная
     * @return выражение, представляющее производную
     */
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

    /**
     * Вычисляет выражение при заданных значениях переменных.
     * Строка assignments должна иметь вид: "x = 10; y = 13"
     */
    public int eval(String assignments) {
        Parser parser = new Parser();
        Map<String, Integer> vars = parser.parseAssignments(assignments);
        return evaluate(vars);
    }
}
