package ru.nsu.dashkovskii;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Класс, представляющий колоду карт.
 */
public class Deck {
    private final List<Card> cards = new ArrayList<>();
    private final Random random = new Random();

    /**
     * Создает колоду из заданного количества колод.
     *
     * @param numDecks количество колод
     */
    public Deck(int numDecks) {
        String[] suits = {"Черви", "Бубны", "Трефы", "Пики"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Валет", "Дама", "Король", "Туз"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        for (int d = 0; d < numDecks; d++) {
            for (String suit : suits) {
                for (int j = 0; j < ranks.length; j++) {
                    cards.add(new Card(suit, ranks[j], values[j]));
                }
            }
        }
        shuffle();
    }

    /**
     * Перемешивает колоду.
     */
    public void shuffle() {
        Collections.shuffle(cards, random);
    }

    /**
     * Раздает одну карту из колоды.
     *
     * @return карта
     */
    public Card dealCard() {
        return cards.remove(0);
    }

    /**
     * Возвращает количество оставшихся карт в колоде.
     *
     * @return количество карт
     */
    public int size() {
        return cards.size();
    }
}