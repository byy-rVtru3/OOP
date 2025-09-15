package ru.nsu.dashkovskii;

/**
 * Класс, представляющий одну карту.
 */
public class Card {
    public enum Suit {
        HEARTS("Черви"),
        DIAMONDS("Бубны"),
        CLUBS("Трефы"),
        SPADES("Пики");

        private final String displayName;

        Suit(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public enum Rank {
        TWO("2"),
        THREE("3"),
        FOUR("4"),
        FIVE("5"),
        SIX("6"),
        SEVEN("7"),
        EIGHT("8"),
        NINE("9"),
        TEN("10"),
        JACK("Валет"),
        QUEEN("Дама"),
        KING("Король"),
        ACE("Туз");

        private final String displayName;

        Rank(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

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