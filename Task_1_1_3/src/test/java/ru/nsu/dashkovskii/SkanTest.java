package ru.nsu.dashkovskii;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import ru.nsu.dashkovskii.parcer.Scan;

/**
 * Тестовый класс для проверки функциональности класса Skan.
 */
public class SkanTest {

    /**
     * Тестирует приватный метод isValidVariable с помощью рефлексии.
     */
    @Test
    public void testIsValidVariableName() {
        Scan scanner = new Scan();

        try {
            java.lang.reflect.Method method = Scan.class
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
     * Тестирует создание экземпляра Skan.
     */
    @Test
    public void testScannerCreation() {
        Scan scanner = new Scan();
        assertNotNull(scanner);
    }
}
