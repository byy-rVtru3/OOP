package ru.nsu.dashkovskii;

import java.util.Scanner;

/**
 * Класс для взаимодействия с пользователем (вывод сообщений и получение ввода).
 */
public class View {
    private final Scanner scanner;

    /**
     * Конструктор класса View.
     *
     * @param scanner источник ввода пользователя
     */
    public View(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Выводит сообщение без перехода на новую строку.
     *
     * @param message сообщение
     */
    public void print(String message) {
        System.out.print(message);
    }

    /**
     * Выводит сообщение с переходом на новую строку.
     *
     * @param message сообщение
     */
    public void println(String message) {
        System.out.println(message);
    }

    /**
     * Выводит приветственное сообщение.
     */
    public void printWelcome() {
        println("Добро пожаловать в Блэкджек!");
    }

    /**
     * Выводит номер текущего раунда.
     *
     * @param round номер раунда
     */
    public void printRound(int round) {
        println("\nРаунд " + round);
    }

    /**
     * Сообщает о раздаче карт.
     */
    public void printDeal() {
        println("Дилер раздал карты");
    }

    /**
     * Показывает карты игрока.
     *
     * @param hand рука игрока
     */
    public void printPlayerHand(Hand hand) {
        println("Ваши карты: " + hand);
    }

    /**
     * Показывает первую карту дилера.
     *
     * @param card карта дилера
     */
    public void printDealerFirstCard(Card card) {
        println("Карты дилера: [" + card + ", <закрытая карта>]");
    }

    /**
     * Сообщает о блэкджеке у игрока.
     */
    public void printBlackjack() {
        println("У вас Блэкджек! Вы выиграли раунд!");
    }

    /**
     * Сообщает о ходе игрока и предлагает выбор.
     */
    public void printPlayerTurn() {
        println("Ваш ход\nВведите '1', чтобы взять карту, и '0', чтобы остановиться.");
    }

    /**
     * Получает выбор игрока.
     *
     * @return выбор игрока (1 — взять карту, 0 — остановиться)
     */
    public int getPlayerChoice() {
        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice == 1 || choice == 0) {
                    return choice;
                } else {
                    println("Ошибка: введите только 1 (взять карту) или 0 (остановиться).");
                }
            } else {
                println("Ошибка: введите только 1 (взять карту) или 0 (остановиться).");
                scanner.next();
            }
            print("Введите '1', чтобы взять карту, и '0', чтобы остановиться: ");
        }
    }

    /**
     * Сообщает о взятой игроком карте.
     *
     * @param c карта
     */
    public void printPlayerDraw(Card c) {
        println("Вы открыли карту " + c);
    }

    /**
     * Показывает карты игрока после взятия карты.
     *
     * @param hand рука игрока
     */
    public void printPlayerHandAfterDraw(Hand hand) {
        println("Ваши карты: " + hand);
    }

    /**
     * Сообщает о переборе у игрока.
     */
    public void printPlayerBust() {
        println("Перебор! Вы проиграли раунд.");
    }

    /**
     * Сообщает о ходе дилера.
     */
    public void printDealerTurn() {
        println("Ход дилера");
    }

    /**
     * Показывает скрытую карту дилера.
     *
     * @param card карта дилера
     */
    public void printDealerReveal(Card card) {
        println("Дилер открывает закрытую карту " + card);
    }

    /**
     * Показывает карты дилера.
     *
     * @param hand рука дилера
     */
    public void printDealerHand(Hand hand) {
        println("Карты дилера: " + hand);
    }

    /**
     * Сообщает о взятой дилером карте.
     *
     * @param c карта
     */
    public void printDealerDraw(Card c) {
        println("Дилер открывает карту " + c);
    }

    /**
     * Сообщает о переборе у дилера.
     */
    public void printDealerBust() {
        println("Дилер перебрал! Вы выиграли ра��нд!");
    }

    /**
     * Сообщает о победе игрока.
     */
    public void printPlayerWin() {
        println("Вы выиграли раунд!");
    }

    /**
     * Сообщает о победе дилера.
     */
    public void printDealerWin() {
        println("Вы проиграли раунд!");
    }

    /**
     * Сообщает о ничьей.
     */
    public void printDraw() {
        println("Ничья!");
    }

    /**
     * Показывает текущий счет.
     *
     * @param playerScore счет игрока
     * @param dealerScore счет дилера
     */
    public void printScore(int playerScore, int dealerScore) {
        println("Счет " + playerScore + ":" + dealerScore);
    }
}
