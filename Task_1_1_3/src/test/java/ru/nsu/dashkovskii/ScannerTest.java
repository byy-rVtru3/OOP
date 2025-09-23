package ru.nsu.dashkovskii;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для проверки функциональности класса Scanner.
 */
public class ScannerTest {

    /**
     * Тестирует приватный метод isValidVariable с помощью рефлексии.
     */
    @Test
    public void testIsValidVariableName() {
        Scanner scanner = new Scanner();

        // Используем рефлексию для тестирования приватного метода
        try {
            java.lang.reflect.Method method = Scanner.class
                    .getDeclaredMethod("isValidVariable", String.class);
            method.setAccessible(true);

            assertTrue((Boolean) method.invoke(scanner, "x"));
            assertTrue((Boolean) method.invoke(scanner, "foo"));
            assertTrue((Boolean) method.invoke(scanner, "var123"));
            assertTrue((Boolean) method.invoke(scanner, "myVariable"));

            assertFalse((Boolean) method.invoke(scanner, ""));
            assertFalse((Boolean) method.invoke(scanner, "123"));
            assertFalse((Boolean) method.invoke(scanner, "123abc"));
            assertFalse((Boolean) method.invoke(scanner, "var!"));
            assertFalse((Boolean) method.invoke(scanner, "my-var"));
            assertFalse((Boolean) method.invoke(scanner, "my var"));

        } catch (Exception e) {
            fail("Ошибка при тестировании isValidVariable: " + e.getMessage());
        }
    }

    /**
     * Тестирует создание экземпляра Scanner.
     */
    @Test
    public void testScannerCreation() {
        Scanner scanner = new Scanner();
        assertNotNull(scanner);
    }

    // Примечание: Методы readExpression(), readAssignments() и readDiffVariable()
    // сложно тестировать unit-тестами, так как они требуют пользовательского ввода.
    // Для их тестирования потребовались бы моки или интеграционные тесты.
}
