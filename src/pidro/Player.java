package pidro;

import pidro.deck.card.Card;
import pidro.deck.card.CardValue;
import pidro.deck.card.Suit;

import java.util.*;

public class Player {

    private final String name;

    private List<Card> cards;

    private Stack<Card> playedCards;

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
            if(card.getSuit() != chosenSuit && !card.isOutOfSuitPidro(chosenSuit)) {
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
        this.cards.sort(Card.cardSortComparator);
    }

    String cardsAsString() {
        return this.cards.toString();
    }

    @Override
    public String toString() {
        return name;
    }
}
