package ru.nsu.dashkovskii;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BlackjackTest {

    @Test
    void testBlackjackCreation() {
        Blackjack game = new Blackjack(1);
        assertNotNull(game);
    }
}
