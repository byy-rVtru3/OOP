package ru.nsu.dashkovskii;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class BlackjackTest {

    @Test
    void testBlackjackCreation() {
        Blackjack game = new Blackjack(1);
        assertNotNull(game);
    }
}
