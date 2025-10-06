package ru.nsu.dashkovskii;

import ru.nsu.dashkovskii.ast.Expression;
import ru.nsu.dashkovskii.parcer.Papcep;
import ru.nsu.dashkovskii.parcer.Scan;

/**
 * Главный класс приложения для работы с математическими выражениями.
 */
public class Main {
    /**
     * Главный метод приложения.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scan scanner = new Scan();
        Papcep parser = new Papcep();

        try {
            String exprStr = scanner.readExpression();
            Expression expr = parser.parse(exprStr);

            String assignments = scanner.readAssignments();

            System.out.println("Исходное выражение: " + expr);

            int result = expr.eval(assignments);
            System.out.println("Значение при " + assignments + ": " + result);

            String diffVar = scanner.readDiffVariable();

            Expression de = expr.derivative(diffVar);
            System.out.println("Производная по " + diffVar + ": " + de);

            int diffResult = de.eval(assignments);
            System.out.println("Значение производной: " + diffResult);


        } catch (ArithmeticException e) {
            System.out.println("Ошибка: Деление на ноль при вычислении выражения.");
            System.out.println("Программа завершена.");
        } catch (Exception e) {
            System.out.println("Критическая ошибка: " + e.getMessage());
            System.out.println("Программа завершена.");
        }
    }
}