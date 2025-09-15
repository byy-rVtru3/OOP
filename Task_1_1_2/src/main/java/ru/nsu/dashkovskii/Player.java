package ru.nsu.dashkovskii;

/**
 * Класс, представляющий игрока.
 */
public class Player {
    private final String name;
    private final Hand hand = new Hand();

    /**
     * Конструктор игрока.
     */
    public Player() {
        this.name = "Игрок";
    }

    /**
     * Конструктор с именем.
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Возвращает руку игрока.
     *
     * @return рука игрока
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Возвращает имя игрока.
     *
     * @return имя игрока
     */
    public String getName() {
        return name;
    }

    /**
     * Класс дилера как наследник игрока.
     */
    public static class Dealer extends Player {
        public Dealer() {
            super("Дилер");
        }
    }
}
