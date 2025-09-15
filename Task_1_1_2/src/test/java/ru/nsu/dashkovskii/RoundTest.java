package ru.nsu.dashkovskii;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Round.
 */
public class RoundTest {

    /**
     * Тестовая колода, возвращающая карты в заданном порядке.
     */
    static class TestDeck extends Deck {
        private final Queue<Card> queue;

        public TestDeck(Card... cards) {
            super(1);
            queue = new LinkedList<>();
            for (Card c : cards) {
                queue.add(c);
            }
        }

        @Override
        public Card dealCard() {
            return queue.poll();
        }
    }

    /**
     * Проверяет, что раунд завершается корректно при блэкджеке у игрока.
     */
    @Test
    void testPlayerBlackjack() {
        // Порядок: игрок1, игрок2, дилер1, дилер2
        Card player1 = new Card(Card.Suit.SPADES, Card.Rank.ACE, 11);
        Card player2 = new Card(Card.Suit.HEARTS, Card.Rank.KING, 10);
        Card dealer1 = new Card(Card.Suit.CLUBS, Card.Rank.FIVE, 5);
        Card dealer2 = new Card(Card.Suit.DIAMONDS, Card.Rank.SIX, 6);

        Deck deck = new TestDeck(player1, player2, dealer1, dealer2);
        Player player = new Player();
        Player.Dealer dealer = new Player.Dealer();
        View view = new View(new Scanner(System.in)) {
            @Override
            public int getPlayerChoice() {
                return 0;
            }
        };

        Round.Result result = Round.playRound(player, dealer, deck, view);
        assertEquals(Round.Result.PLAYER_WIN, result);
    }

    /**
     * Проверяет, что ничья корректно определяется.
     */
    @Test
    void testDraw() {
        // Порядок: игрок1, игрок2, дилер1, дилер2
        Card player1 = new Card(Card.Suit.SPADES, Card.Rank.TEN, 10);
        Card player2 = new Card(Card.Suit.HEARTS, Card.Rank.NINE, 9);
        Card dealer1 = new Card(Card.Suit.CLUBS, Card.Rank.TEN, 10);
        Card dealer2 = new Card(Card.Suit.DIAMONDS, Card.Rank.NINE, 9);

        Deck deck = new TestDeck(player1, player2, dealer1, dealer2);
        Player player = new Player();
        Player.Dealer dealer = new Player.Dealer();
        View view = new View(new Scanner(System.in)) {
            @Override
            public int getPlayerChoice() {
                return 0;
            }
        };

        Round.Result result = Round.playRound(player, dealer, deck, view);
        assertEquals(Round.Result.DRAW, result);
    }
}
