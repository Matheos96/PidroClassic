package pidro;

import pidro.deck.card.Suit;
import pidro.exception.GameNotReadyException;
import pidro.exception.IllegalBidException;
import pidro.exception.LastMustBidException;
import pidro.exception.TeamIsFullException;

public class Tester {

    public static void main(String[] args) {
        Game pidroGame = new Game();
        try {
            pidroGame.joinTeam("vi", "Matheos");
            pidroGame.joinTeam("vi", "Daniel");
            pidroGame.joinTeam("de", "Erik");
            pidroGame.joinTeam("de", "Matts");

            pidroGame.findInitDealer();
            System.out.printf("%s buckar...\n", pidroGame.getPreviousPlayer());
            pidroGame.splitDeck(2);
            pidroGame.initialDeal();
            pidroGame.printPlayerCards();

            pidroGame.bidCurrentPlayerAndNext(6);
            pidroGame.bidCurrentPlayerAndNext(0);
            pidroGame.bidCurrentPlayerAndNext(9);
            pidroGame.bidCurrentPlayerAndNext(0);
            if (pidroGame.biddingIsDone()) {
                System.out.printf("%s is choosing suit...\n", pidroGame.getWinningBidPlayer());
                pidroGame.setCurrentSuit(Suit.DIAMONDS);

            }


        }
        catch (TeamIsFullException | GameNotReadyException e) {
            System.out.println(e);
        } catch (LastMustBidException | IllegalBidException e) {
            e.printStackTrace();
        }


    }
}
