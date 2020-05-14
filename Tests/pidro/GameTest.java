package pidro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pidro.exception.TeamIsFullException;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game pidroGame;

    @BeforeEach
    void setUp() {
        this.pidroGame = new Game();
    }

    @Test
    void joinTeam() throws TeamIsFullException {
        pidroGame.joinTeam(TeamName.WE, "player1");
        pidroGame.joinTeam(TeamName.WE, "player2");

        assertThrows(TeamIsFullException.class, () -> {
            pidroGame.joinTeam(TeamName.WE, "player5");
        });

        pidroGame.joinTeam(TeamName.THEY, "player3");
        pidroGame.joinTeam(TeamName.THEY, "player4");

        assertThrows(TeamIsFullException.class, () -> {
            pidroGame.joinTeam(TeamName.THEY, "player5");
        });

        assertEquals("player1", pidroGame.getCurrentPlayer().toString());

    }

    @Test
    void isReady() throws TeamIsFullException {
        pidroGame.joinTeam(TeamName.WE, "player1");

        assertFalse(pidroGame.isReady());

        pidroGame.joinTeam(TeamName.WE, "player2");
        pidroGame.joinTeam(TeamName.THEY, "player3");

        assertFalse(pidroGame.isReady());

        pidroGame.joinTeam(TeamName.THEY, "player4");

        assertTrue(pidroGame.isReady());

    }

    @Test
    void biddingIsDone() {
    }

    @Test
    void findInitDealer() {
    }

    @Test
    void splitDeck() {
    }

    @Test
    void initialDeal() {
    }

    @Test
    void secondDeal() {
    }

    @Test
    void bidCurrentPlayerAndNext() {
    }

    @Test
    void setCurrentSuit() {
    }

    @Test
    void sortPlayerCards() {
    }

    @Test
    void popIrrelevantCards() {
    }

    @Test
    void nextPlayer() {
    }

    @Test
    void peekNextPlayer() {
    }

    @Test
    void getPreviousPlayer() {
    }

    @Test
    void getCurrentPlayer() {
    }

    @Test
    void getWinningBidPlayer() {
    }

    @Test
    void printPlayerCards() {
    }
}