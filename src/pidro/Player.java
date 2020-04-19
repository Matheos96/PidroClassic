package pidro;

import pidro.deck.card.Card;

import java.util.*;

public class Player {

    private String name;

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
