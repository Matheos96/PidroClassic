package pidro;

import pidro.deck.card.Card;
import pidro.deck.card.Suit;

import java.util.*;

public class Player {

    private final String name;

    private final List<Card> cards;

    private final Stack<Card> playedCards;

    Player(String name) {
        this.cards = new ArrayList<>();
        this.playedCards = new Stack<>();
        this.name = name;
    }

    void giveCard(Card card) {
        cards.add(card);
    }

    /**
     * Removes all the cards that cannot be played for the current round.
     * @param chosenSuit The suit currently being played.
     */
    void removeIrrelevantCards(Suit chosenSuit) {

        //Find indices of cards to remove
        List<Integer> toBeRemoved = new ArrayList<>();
        Card card;
        for(int i = 0; i < cards.size(); i++) {
            card = cards.get(i);
            //If the card is not of the given suit AND not the second out of suit pidro
            if(card.getSuit() != chosenSuit && !card.isRightColorPidro(chosenSuit)) {
                toBeRemoved.add(i);
            }
        }

        //Need to reverse the list of indices to be removed in order to remove from the end
        //to not impact the indices for removals
        Collections.reverse(toBeRemoved);

        //Remove the cards
        for(int i : toBeRemoved) {
            cards.remove(i);
        }
    }

    void sortCardsBySuit(){
        Collections.sort(this.cards);
    }

    String cardsAsString() {
        return this.cards.toString();
    }

    /**
     * Calculates and returns the cards needed in order to get the required six cards during the second deal.
     * @return cards needed to reach six
     */
    int neededCards() { return 6 - this.cards.size(); }

    @Override
    public String toString() {
        return name;
    }
}
