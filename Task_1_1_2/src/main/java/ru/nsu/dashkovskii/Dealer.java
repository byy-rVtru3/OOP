package ru.nsu.dashkovskii;

public class Dealer {
    private final String name;
    private final Hand hand = new Hand();

    public Dealer() {
        this.name = "Дилер";
    }

    public Hand getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }
}
