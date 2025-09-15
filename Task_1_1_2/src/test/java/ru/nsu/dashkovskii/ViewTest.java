package ru.nsu.dashkovskii;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса View.
 */
public class ViewTest {

    /**
     * Проверяет, что getPlayerChoice корректно читает ввод.
     */
    @Test
    void testGetPlayerChoice() {
        String input = "1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        View view = new View(scanner);
        int choice = view.getPlayerChoice();
        assertEquals(1, choice);
    }

    /**
     * Проверяет, что методы print и println не выбрасывают исключения.
     */
    @Test
    void testPrintMethods() {
        View view = new View(new Scanner(System.in));
        assertDoesNotThrow(() -> {
            view.print("test");
            view.println("test");
        });
    }
}
