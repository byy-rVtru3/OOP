package ru.nsu.dashkovskii;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HandTest {
    @Test
    void testScoreWithoutAce() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.TEN, 10));
        hand.addCard(new Card(Suit.HEARTS, Rank.NINE, 9));
        assertEquals(19, hand.getScore());
        assertFalse(hand.isBust());
        assertFalse(hand.isBlackjack());
    }

    @Test
    void testAceAs11() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.ACE, 11));
        hand.addCard(new Card(Suit.HEARTS, Rank.NINE, 9));
        assertEquals(20, hand.getScore());
    }

    @Test
    void testAceAs1() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.ACE, 1));
        hand.addCard(new Card(Suit.HEARTS, Rank.NINE, 9));
        hand.addCard(new Card(Suit.DIAMONDS, Rank.FIVE, 5));
        assertEquals(15, hand.getScore());
    }

    @Test
    void testBlackjack() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.ACE, 11));
        hand.addCard(new Card(Suit.HEARTS, Rank.KING, 10));
        assertTrue(hand.isBlackjack());
    }

    @Test
    void testBust() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.TEN, 10));
        hand.addCard(new Card(Suit.HEARTS, Rank.NINE, 9));
        hand.addCard(new Card(Suit.DIAMONDS, Rank.FIVE, 5));
        assertTrue(hand.isBust());
    }
}
