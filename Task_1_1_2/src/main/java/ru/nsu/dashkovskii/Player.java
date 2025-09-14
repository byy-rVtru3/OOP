package ru.nsu.dashkovskii;

public class Player {
    private final String name;
    private final Hand hand = new Hand();

    public Player() {
        this.name = "Игрок";
    }

    public Hand getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

}
