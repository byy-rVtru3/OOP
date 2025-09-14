package ru.nsu.dashkovskii;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CardTest {
    @Test
    void testCardFields() {
        Card card = new Card("Пики", "Туз", 11);
        assertEquals("Пики", card.getSuit());
        assertEquals("Туз", card.getRank());
        assertEquals(11, card.getValue());
        assertTrue(card.toString().contains("Туз"));
    }
}
