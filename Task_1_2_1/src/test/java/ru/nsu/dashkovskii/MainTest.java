package ru.nsu.dashkovskii;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Main.
 */
class MainTest {

    @Test
    void testMainMethodWithFile() {
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
}
