package ru.nsu.dashkovskii;

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
