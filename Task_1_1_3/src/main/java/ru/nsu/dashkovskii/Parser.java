package ru.nsu.dashkovskii;

import java.util.HashMap;
import java.util.Map;

public class Parser {
    private String string;

    public static Expression parse(String s) {
        s = s.trim();

        if (s.isEmpty()) {
            throw new IllegalArgumentException("Пустое выражение");
        }

        // Проверяем корректность скобок
        if (!isValidParentheses(s)) {
            throw new IllegalArgumentException("Некорректная расстановка скобок");
        }

        if (s.startsWith("(") && s.endsWith(")")) {
            // Убираем внешние скобки и ищем главный оператор
            int depth = 0;
            boolean foundOperator = false;

            for (int i = 1; i < s.length() - 1; ++i) {
                char c = s.charAt(i);
                if (c == '(') depth++;
                if (c == ')') depth--;
                if (depth == 0 && (c == '+' || c == '-' || c == '*' || c == '/')) {
                    foundOperator = true;
                    String leftStr = s.substring(1, i).trim();
                    String rightStr = s.substring(i + 1, s.length() - 1).trim();

                    if (leftStr.isEmpty() || rightStr.isEmpty()) {
                        throw new IllegalArgumentException("Отсутствует операнд рядом с оператором '" + c + "'");
                    }

                    Expression left = parse(leftStr);
                    Expression right = parse(rightStr);

                    switch (c) {
                        case '+': return new Add(left, right);
                        case '-': return new Sub(left, right);
                        case '*': return new Mul(left, right);
                        case '/': return new Div(left, right);
                    }
                }
            }

            if (!foundOperator) {
                // Если в скобках нет операторов, парсим содержимое
                return parse(s.substring(1, s.length() - 1));
            }
        }

        // Проверяем на число
        try {
            return new Number(Integer.parseInt(s));
        } catch (NumberFormatException ignored) {}

        // Проверяем на переменную
        if (isValidVariable(s)) {
            return new Variable(s);
        }

        throw new IllegalArgumentException("Некорректное выражение: '" + s + "'");
    }

    private static boolean isValidParentheses(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') count++;
            if (c == ')') count--;
            if (count < 0) return false;
        }
        return count == 0;
    }

    private static boolean isValidVariable(String s) {
        if (s.isEmpty()) return false;
        if (!Character.isLetter(s.charAt(0))) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) return false;
        }
        return true;
    }

    public static Map<String, Integer> parseAssignments(String assignments) {
        Map<String, Integer> vars = new HashMap<>();

        if (assignments == null || assignments.trim().isEmpty()) {
            return vars;
        }

        String[] pairs = assignments.split(";");
        for (String pair : pairs) {
            pair = pair.trim();
            if (pair.isEmpty()) continue;

            String[] kv = pair.split("=");
            if (kv.length != 2) {
                throw new IllegalArgumentException("Некорректный формат присваивания: '" + pair + "'. Ожидается формат 'переменная = значение'");
            }

            String varName = kv[0].trim();
            String valueStr = kv[1].trim();

            if (!isValidVariable(varName)) {
                throw new IllegalArgumentException("Некорректное имя переменной: '" + varName + "'. Переменная должна начинаться с буквы и содержать только буквы и цифры");
            }

            try {
                int value = Integer.parseInt(valueStr);
                vars.put(varName, value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Некорректное значение переменной: '" + valueStr + "'. Ожидается целое число");
            }
        }

        return vars;
    }
}
