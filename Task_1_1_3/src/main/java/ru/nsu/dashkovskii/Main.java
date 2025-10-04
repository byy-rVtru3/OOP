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
        Parser parser = new Parser();

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

        } catch (Exception e) {
            System.out.println("Критическая ошибка: " + e.getMessage());
            System.out.println("Программа завершена.");
        }
    }
}