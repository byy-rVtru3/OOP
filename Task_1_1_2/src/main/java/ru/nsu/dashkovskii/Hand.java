package ru.nsu.dashkovskii;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий руку игрока или дилера.
 */
public class Hand {
    private final List<Card> cards = new ArrayList<>();

    /**
     * Добавляет карту в руку.
     *
     * @param card карта
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Возвращает сумму очков в руке.
     *
     * @return сумма очков
     */
    public int getScore() {
        int score = 0;
        int aceCount = 0;

        for (Card card : cards) {
            score += card.getValue();
            if (card.getRank().equals("Туз")) {
                aceCount++;
            }
        }

        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }

        return score;
    }

    /**
     * Проверяет, есть ли перебор (больше 21).
     *
     * @return true, если перебор
     */
    public boolean isBust() {
        return getScore() > 21;
    }

    /**
     * Проверяет, есть ли блэкджек (21 очко с двух карт).
     *
     * @return true, если блэкджек
     */
    public boolean isBlackjack() {
        return cards.size() == 2 && getScore() == 21;
    }

    /**
     * Возвращает список карт в руке.
     *
     * @return список карт
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Возвращает строковое представление руки.
     *
     * @return строка с картами и очками
     */
    @Override
    public String toString() {
        return cards.toString() + " > " + getScore();
    }
}
