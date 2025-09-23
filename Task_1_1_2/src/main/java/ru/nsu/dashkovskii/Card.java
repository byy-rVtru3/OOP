package ru.nsu.dashkovskii;

/**
 * Класс, представляющий одну карту.
 */
public class Card {

    private final Suit suit;
    private final Rank rank;
    private final int value;

    /**
     * Конструктор карты.
     *
     * @param suit масть
     * @param rank достоинство
     * @param value значение карты
     */
    public Card(Suit suit, Rank rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    /**
     * Возвращает значение карты.
     *
     * @return значение
     */
    public int getValue() {
        return value;
    }

    /**
     * Возвращает достоинство карты.
     *
     * @return достоинство
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Возвращает масть карты.
     *
     * @return масть
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Возвращает строковое представление карты.
     *
     * @return строка с картой
     */
    @Override
    public String toString() {
        return rank + " " + suit + " (" + value + ") ";
    }
}