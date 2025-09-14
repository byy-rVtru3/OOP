package ru.nsu.dashkovskii;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerDealerTest {
    @Test
    void testPlayerNameAndHand() {
        Player player = new Player();
        assertEquals("Игрок", player.getName());
        assertNotNull(player.getHand());
    }

    @Test
    void testDealerNameAndHand() {
        Dealer dealer = new Dealer();
        assertEquals("Дилер", dealer.getName());
        assertNotNull(dealer.getHand());
    }
}

