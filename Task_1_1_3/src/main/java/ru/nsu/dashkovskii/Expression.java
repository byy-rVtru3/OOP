package ru.nsu.dashkovskii;

import java.util.Map;

public abstract class Expression {

    public abstract int evaluate(Map<String, Integer> variables);

    public abstract Expression derivative(String var);

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
