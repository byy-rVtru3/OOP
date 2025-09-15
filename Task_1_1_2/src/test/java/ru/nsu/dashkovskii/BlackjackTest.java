package ru.nsu.dashkovskii;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Blackjack.
 */
class BlackjackTest {

    /**
     * Проверяет создание игры с одной колодой.
     */
    @Test
    void testBlackjackCreation() {
        Blackjack game = new Blackjack(1);
        assertNotNull(game);
    }

    /**
     * Проверяет, что игра запускается без исключений (один раунд, ввод с консоли).
     */
    @Test
    void testBlackjackPlayDoesNotThrow() {
        // Сначала ввод количества колод, затем выбор игрока
        String input = "1\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        Blackjack game = new Blackjack(scanner);
        assertDoesNotThrow(() -> game.play(1));
    }
}
