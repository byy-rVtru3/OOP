package ru.nsu.dashkovskii;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DeckTest {

    @Test
    void oneDeckHas52Cards() {
        Deck deck = new Deck(1);
        assertEquals(52, deck.size());
    }

    @Test
    void twoDecksHave104Cards() {
        Deck deck = new Deck(2);
        assertEquals(104, deck.size());
    }

    @Test
    void dealCardReducesDeckSize() {
        Deck deck = new Deck(1);
        int before = deck.size();
        deck.dealCard();
        assertEquals(before - 1, deck.size());
    }

    @Test
    void dealAllCardsLeavesZero() {
        Deck deck = new Deck(1);
        for (int i = 0; i < 52; i++) {
            deck.dealCard();
        }
        assertEquals(0, deck.size());
    }
}
