package ru.nsu.dashkovskii;

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

        try {
            String exprStr = scanner.readExpression();
            Expression expr = Parser.parse(exprStr);

            String assignments = scanner.readAssignments();

            System.out.print("Исходное выражение: ");
            expr.print();

            int result = expr.eval(assignments);
            System.out.println("Значение при " + assignments + ": " + result);

            String diffVar = scanner.readDiffVariable();

            Expression de = expr.derivative(diffVar);
            System.out.print("Производная по " + diffVar + ": ");
            de.print();

            int diffResult = de.eval(assignments);
            System.out.println("Значение производной: " + diffResult);

        } catch (Exception e) {
            System.out.println("Критическая ошибка: " + e.getMessage());
            System.out.println("Программа завершена.");
        }
    }
}