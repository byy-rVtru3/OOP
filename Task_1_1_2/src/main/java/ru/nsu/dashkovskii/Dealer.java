package ru.nsu.dashkovskii;

/**
 * Класс, представляющий дилера.
 */
public class Dealer {
    private final String name;
    private final Hand hand = new Hand();

    /**
     * Конструктор дилера.
     */
    public Dealer() {
        this.name = "Дилер";
    }

    /**
     * Возвращает руку дилера.
     * @return рука дилера
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Возвращает имя дилера.
     * @return имя дилера
     */
    public String getName() {
        return name;
    }
}
