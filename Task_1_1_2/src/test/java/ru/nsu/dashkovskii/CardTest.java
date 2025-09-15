package ru.nsu.dashkovskii;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CardTest {
    @Test
    void testCardFields() {
        Card card = new Card(Card.Suit.SPADES, Card.Rank.ACE, 11);
        assertEquals(Card.Suit.SPADES, card.getSuit());
        assertEquals(Card.Rank.ACE, card.getRank());
        assertEquals(11, card.getValue());
        assertTrue(card.toString().contains("Туз"));
    }
}
