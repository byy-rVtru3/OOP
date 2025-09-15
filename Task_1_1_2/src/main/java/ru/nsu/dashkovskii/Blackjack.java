package ru.nsu.dashkovskii;

import java.util.Scanner;

/**
 * Класс, реализующий игру Блэкджек.
 */
public class Blackjack {
    private final Scanner scanner;
    private Deck deck;
    private final Player player = new Player();
    private final Player.Dealer dealer = new Player.Dealer();
    private int scorePlayer = 0;
    private int scoreDealer = 0;
    private final View view;

    /**
     * Конструктор по умолчанию. Использует стандартный ввод.
     */
    public Blackjack() {
        this(new Scanner(System.in));
    }

    /**
     * Конструктор с передачей Scanner.
     *
     * @param scanner источник ввода пользователя
     */
    public Blackjack(Scanner scanner) {
        this.scanner = scanner;
        this.view = new View(scanner);
        view.print("Введите количество колод: ");
        int numDecks = scanner.nextInt();
        deck = new Deck(numDecks);
    }

    /**
     * Конструктор с указанием количества колод.
     *
     * @param numDecks количество колод
     */
    public Blackjack(int numDecks) {
        this.scanner = new Scanner(System.in);
        this.view = new View(scanner);
        deck = new Deck(numDecks);
    }

    /**
     * Запускает игру в бесконечном цикле.
     */
    public void play() {
        view.printWelcome();
        int round = 1;

        while (true) {
            view.printRound(round);
            playRound();
            round++;
        }
    }

    /**
     * Запускает игру на заданное количество раундов.
     *
     * @param rounds количество раундов
     */
    public void play(int rounds) {
        view.printWelcome();
        for (int i = 1; i <= rounds; i++) {
            view.printRound(i);
            playRound();
        }
    }

    /**
     * Запускает один раунд игры.
     */
    private void playRound() {
        Round.Result result = Round.playRound(player, dealer, deck, view);
        if (result == Round.Result.PLAYER_WIN) {
            scorePlayer++;
        } else if (result == Round.Result.DEALER_WIN) {
            scoreDealer++;
        }
        view.printScore(scorePlayer, scoreDealer);
    }
}
