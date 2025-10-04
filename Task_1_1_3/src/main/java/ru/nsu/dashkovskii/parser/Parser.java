package ru.nsu.dashkovskii;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для парсинга математических выражений из строки.
 */
public class Parser {

    /**
     * Парсит математическое выражение из строки.
     *
     * @param s строка с выражением
     * @return объект Expression, представляющий выражение
     * @throws IllegalArgumentException если выражение некорректно
     */
    public Expression parse(String s) {
        s = s.trim();

        if (s.isEmpty()) {
            throw new IllegalArgumentException("Пустое выражение");
        }

        if (!isValidParentheses(s)) {
            throw new IllegalArgumentException("Некорректная расстановка скобок");
        }

        return parseExpression(s);
    }

    /**
     * Парсит выражение с учетом приоритета операций.
     */
    private Expression parseExpression(String s) {
        s = s.trim();

        while (s.startsWith("(") && s.endsWith(")") && isMatchingParentheses(s, 0, s.length() - 1)) {
            s = s.substring(1, s.length() - 1).trim();
        }

        int operatorPos = findOperator(s, new char[]{'+', '-'});
        if (operatorPos != -1) {
            char operator = s.charAt(operatorPos);
            String leftStr = s.substring(0, operatorPos).trim();
            String rightStr = s.substring(operatorPos + 1).trim();

            if (leftStr.isEmpty() || rightStr.isEmpty()) {
                throw new IllegalArgumentException(
                        "Отсутствует операнд рядом с оператором '" + operator + "'");
            }

            Expression left = parseExpression(leftStr);
            Expression right = parseExpression(rightStr);

            return operator == '+' ? new Add(left, right) : new Sub(left, right);
        }

        operatorPos = findOperator(s, new char[]{'*', '/'});
        if (operatorPos != -1) {
            char operator = s.charAt(operatorPos);
            String leftStr = s.substring(0, operatorPos).trim();
            String rightStr = s.substring(operatorPos + 1).trim();

            if (leftStr.isEmpty() || rightStr.isEmpty()) {
                throw new IllegalArgumentException(
                        "Отсутствует операнд рядом с оператором '" + operator + "'");
            }

            Expression left = parseExpression(leftStr);
            Expression right = parseExpression(rightStr);

            return operator == '*' ? new Mul(left, right) : new Div(left, right);
        }

        if (isValidVariable(s)) {
            return new Variable(s);
        }

        try {
            return new Number(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректное число: '" + s + "'", e);
        }
    }

    /**
     * Находит позицию оператора с наименьшим приоритетом, не находящегося в скобках.
     */
    private int findOperator(String s, char[] operators) {
        int depth = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == ')') {
                depth++;
            } else if (c == '(') {
                depth--;
            } else if (depth == 0) {
                for (char op : operators) {
                    if (c == op) {
                        return i;
                    }
                }
            }
        }

        return -1;
    }

    /**
     * Проверяет, соответствуют ли скобки на позициях start и end.
     */
    private boolean isMatchingParentheses(String s, int start, int end) {
        if (s.charAt(start) != '(' || s.charAt(end) != ')') {
            return false;
        }

        int depth = 0;
        for (int i = start; i <= end; i++) {
            if (s.charAt(i) == '(') {
                depth++;
            } else if (s.charAt(i) == ')') {
                depth--;
                if (depth == 0 && i < end) {
                    return false;
                }
            }
        }

        return depth == 0;
    }

    /**
     * Проверяет корректность расстановки скобок в строке.
     *
     * @param s строка для проверки
     * @return true, если скобки расставлены корректно
     */
    private boolean isValidParentheses(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                count++;
            }
            if (c == ')') {
                count--;
            }
            if (count < 0) {
                return false;
            }
        }
        return count == 0;
    }

    private boolean isValidVariable(String s) {
        if (s.isEmpty()) {
            return false;
        }
        if (!Character.isLetter(s.charAt(0))) {
            return false;
        }
        for (char c : s.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Парсит строку с присваиваниями переменных.
     *
     * @param assignments строка вида "x = 10; y = 13"
     * @return карта переменных и их значений
     * @throws IllegalArgumentException если формат присваиваний некорректен
     */
    public Map<String, Integer> parseAssignments(String assignments) {
        Map<String, Integer> vars = new HashMap<>();

        if (assignments == null || assignments.trim().isEmpty()) {
            return vars;
        }

        String[] pairs = assignments.split(";");
        for (String pair : pairs) {
            pair = pair.trim();
            if (pair.isEmpty()) {
                continue;
            }

            String[] kv = pair.split("=");
            if (kv.length != 2) {
                throw new IllegalArgumentException(
                        "Некорректный формат присваивания: '" + pair
                        + "'. Ожидается формат 'переменная = значение'");
            }

            String varName = kv[0].trim();
            String valueStr = kv[1].trim();

            if (!isValidVariable(varName)) {
                throw new IllegalArgumentException(
                        "Некорректное имя переменной: '" + varName
                        + "'. Переменная должна начинаться с буквы и содержать только "
                        + "буквы и цифры");
            }

            try {
                int value = Integer.parseInt(valueStr);
                vars.put(varName, value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Некорректное значение переменной: '" + valueStr
                        + "'. Ожидается целое число");
            }
        }

        return vars;
    }
}
