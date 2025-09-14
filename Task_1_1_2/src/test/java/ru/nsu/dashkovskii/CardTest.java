package ru.nsu.dashkovskii;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
