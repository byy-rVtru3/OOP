package ru.nsu.dashkovskii;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class HandTest {
    @Test
    void testScoreWithoutAce() {
        Hand hand = new Hand();
        hand.addCard(new Card("Пики", "10", 10));
        hand.addCard(new Card("Черви", "9", 9));
        assertEquals(19, hand.getScore());
        assertFalse(hand.isBust());
        assertFalse(hand.isBlackjack());
    }

    @Test
    void testAceAs11() {
        Hand hand = new Hand();
        hand.addCard(new Card("Пики", "Туз", 11));
        hand.addCard(new Card("Черви", "9", 9));
        assertEquals(20, hand.getScore());
    }

    @Test
    void testAceAs1() {
        Hand hand = new Hand();
        hand.addCard(new Card("Пики", "Туз", 11));
        hand.addCard(new Card("Черви", "9", 9));
        hand.addCard(new Card("Бубны", "5", 5));
        assertEquals(15, hand.getScore());
    }

    @Test
    void testBlackjack() {
        Hand hand = new Hand();
        hand.addCard(new Card("Пики", "Туз", 11));
        hand.addCard(new Card("Черви", "Король", 10));
        assertTrue(hand.isBlackjack());
    }

    @Test
    void testBust() {
        Hand hand = new Hand();
        hand.addCard(new Card("Пики", "10", 10));
        hand.addCard(new Card("Черви", "9", 9));
        hand.addCard(new Card("Бубны", "5", 5));
        assertTrue(hand.isBust());
    }
}
