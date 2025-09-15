package ru.nsu.dashkovskii;

/**
 * Класс, реализующий один раунд игры Блэкджек.
 */
public class Round {
    /**
     * Возможные результаты раунда.
     */
    public enum Result {
        PLAYER_WIN, DEALER_WIN, DRAW
    }

    /**
     * Запускает один раунд игры Блэкджек.
     *
     * @param player игрок
     * @param dealer дилер
     * @param deck   колода ка��т
     * @param view   объект для взаимодейств��я с пользователем
     * @return результат раунда
     */
    public static Result playRound(Player player, Player.Dealer dealer, Deck deck, View view) {
        player.getHand().getCards().clear();
        dealer.getHand().getCards().clear();

        player.getHand().addCard(deck.dealCard());
        player.getHand().addCard(deck.dealCard());
        dealer.getHand().addCard(deck.dealCard());
        dealer.getHand().addCard(deck.dealCard());

        view.printDeal();
        view.printPlayerHand(player.getHand());
        view.printDealerFirstCard(dealer.getHand().getCards().get(0));

        if (player.getHand().isBlackjack()) {
            view.printBlackjack();
            return Result.PLAYER_WIN;
        }

        while (true) {
            view.printPlayerTurn();
            int choice = view.getPlayerChoice();
            if (choice == 1) {
                Card c = deck.dealCard();
                player.getHand().addCard(c);
                view.printPlayerDraw(c);
                view.printPlayerHandAfterDraw(player.getHand());
                if (player.getHand().isBust()) {
                    view.printPlayerBust();
                    return Result.DEALER_WIN;
                }
            } else {
                break;
            }
        }

        view.printDealerTurn();
        view.printDealerReveal(dealer.getHand().getCards().get(1));
        view.printDealerHand(dealer.getHand());

        while (dealer.getHand().getScore() < 17) {
            Card c = deck.dealCard();
            dealer.getHand().addCard(c);
            view.printDealerDraw(c);
            view.printDealerHand(dealer.getHand());
            if (dealer.getHand().isBust()) {
                view.printDealerBust();
                return Result.PLAYER_WIN;
            }
        }

        int playerScore = player.getHand().getScore();
        int dealerScore = dealer.getHand().getScore();
        if (playerScore > dealerScore) {
            view.printPlayerWin();
            return Result.PLAYER_WIN;
        } else if (playerScore < dealerScore) {
            view.printDealerWin();
            return Result.DEALER_WIN;
        } else {
            view.printDraw();
            return Result.DRAW;
        }
    }
}
