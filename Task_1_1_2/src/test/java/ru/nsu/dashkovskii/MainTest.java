package ru.nsu.dashkovskii;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class MainTest {
    @Test
    void testMainDoesNotThrow() {
        // Ввод: 1 колода, действие игрока — остановиться (0)
        String input = "1\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        assertDoesNotThrow(() -> {
            Blackjack game = new Blackjack(scanner);
            game.play(1);
        });
    }
}
