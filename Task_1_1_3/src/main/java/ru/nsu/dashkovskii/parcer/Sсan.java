package ru.nsu.dashkovskii.parcer;

import java.util.Scanner;

/**
 * Класс для чтения пользовательского ввода с консоли.
 */
public class Sсan {
    private final Scanner scanner = new Scanner(System.in);
    private final Papcep parser = new Papcep();

    /**
     * Читает математическое выражение с консоли с валидацией.
     *
     * @return корректное математическое выражение
     */
    public String readExpression() {
        while (true) {
            System.out.println("Введите выражение (например, (3+(2*x))):");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Ошибка: Выражение не может быть пустым. Попробуйте снова.");
                continue;
            }

            try {
                parser.parse(input);
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка парсинга: " + e.getMessage());
                System.out.println(
                        "Примеры корректных выражений: (3+x), (x*2), ((x+1)*(y-2))");
            } catch (Exception e) {
                System.out.println("Неожиданная ошибка: " + e.getMessage());
            }
        }
    }

    /**
     * Читает строку с присваиваниями переменных с консоли с валидацией.
     *
     * @return корректная строка присваиваний
     */
    public String readAssignments() {
        while (true) {
            System.out.print("Введите подстановки (например, x = 10; y = 13): ");
            String input = scanner.nextLine().trim();

            try {
                parser.parseAssignments(input);
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
                System.out.println("Пример корректного формата: x = 5; y = 10");
            } catch (Exception e) {
                System.out.println("Неожиданная ошибка: " + e.getMessage());
            }
        }
    }

    /**
     * Читает имя переменной для дифференцирования с консоли с валидацией.
     *
     * @return корректное имя переменной
     */
    public String readDiffVariable() {
        while (true) {
            System.out.print("Введите переменную для дифференцирования: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Ошибка: Имя переменной не может быть пустым.");
                continue;
            }

            if (!isValidVariable(input)) {
                System.out.println(
                        "Ошибка: Некорректное имя переменной. Переменная должна "
                                + "начинаться с буквы и содержать только буквы и цифры.");
                continue;
            }

            return input;
        }
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
}
