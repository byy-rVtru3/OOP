package ru.nsu.dashkovskii;

import java.util.*;

public class Blackjack {
    private final Scanner scanner = new Scanner(System.in);
    private Deck deck;
    private final Player player = new Player();
    private final Dealer dealer = new Dealer();
    private int scorePlayer = 0;
    private int scoreDealer = 0;

    public Blackjack() {
        System.out.print("Введите количество колод: ");
        int numDecks = scanner.nextInt();
        deck = new Deck(numDecks);
    }

    public void play() {
        System.out.println("Добро пожаловать в Блэкджек!");
        int round = 1;

        while (true) {
            System.out.println("\nРаунд " + round);
            startRound();
            round++;
        }
    }

    private void startRound() {
        player.getHand().getCards().clear();
        dealer.getHand().getCards().clear();

        player.getHand().addCard(deck.dealCard());
        player.getHand().addCard(deck.dealCard());
        dealer.getHand().addCard(deck.dealCard());
        dealer.getHand().addCard(deck.dealCard());

        System.out.println("Дилер раздал карты");
        System.out.println("Ваши карты: " + player.getHand());
        System.out.println("Карты дилера: [" + dealer.getHand().getCards().get(0) + ", <закрытая карта>]");

        if (player.getHand().isBlackjack()) {
            System.out.println("У вас Блэкджек! Вы выиграли раунд!");
            scorePlayer++;
            printScore();
            return;
        }

        while (true) {
            System.out.println("Ваш ход\nВведите '1', чтобы взять карту, и '0', чтобы остановиться.");
            int choice = scanner.nextInt();
            if (choice == 1) {
                Card c = deck.dealCard();
                player.getHand().addCard(c);
                System.out.println("Вы открыли карту " + c);
                System.out.println("Ваши карты: " + player.getHand());
                if (player.getHand().isBust()) {
                    System.out.println("Перебор! Вы проиграли раунд.");
                    scoreDealer++;
                    printScore();
                    return;
                }
            } else {
                break;
            }
        }

        System.out.println("Ход дилера");
        System.out.println("Дилер открывает закрытую карту " + dealer.getHand().getCards().get(1));
        System.out.println("Карты дилера: " + dealer.getHand());

        while (dealer.getHand().getScore() < 17) {
            Card c = deck.dealCard();
            dealer.getHand().addCard(c);
            System.out.println("Дилер открывает карту " + c);
            System.out.println("Карты дилера: " + dealer.getHand());
            if (dealer.getHand().isBust()) {
                System.out.println("Дилер перебрал! Вы выиграли раунд!");
                scorePlayer++;
                printScore();
                return;
            }
        }

        int playerScore = player.getHand().getScore();
        int dealerScore = dealer.getHand().getScore();
        if (playerScore > dealerScore) {
            System.out.println("Вы выиграли раунд!");
            scorePlayer++;
        } else if (playerScore < dealerScore) {
            System.out.println("Вы проиграли раунд!");
            scoreDealer++;
        } else {
            System.out.println("Ничья!");
        }
        printScore();
    }

    private void printScore() {
        System.out.println("Счет " + scorePlayer + ":" + scoreDealer);
    }
}
