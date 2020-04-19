package pidro.deck;

import pidro.deck.card.Card;
import pidro.deck.card.Suit;
import pidro.deck.card.CardValue;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Deck {

    private Stack<Card> cardPile;

    public Deck() {
        this.cardPile = new Stack<>();
        generateCards();
        shuffleCards();
    }

    public Card popTopCard() { return cardPile.pop(); }

    public boolean isEmpty() { return cardPile.isEmpty(); }

    public void poke(int amount) {
        Queue<Card> queue = new LinkedList<>();
        for(int i = 0; i < amount; i++)
            queue.add(cardPile.pop());

        while(!queue.isEmpty())
            cardPile.add(0, queue.poll());

    }

    private void generateCards() {
        for(Suit s : Suit.values()) {
            for(CardValue v : CardValue.values())
                this.cardPile.push(new Card(s, v));
        }
    }

    /**
     * Use Collections built in shuffle method to shuffle the pile. Done twelve times per tradition. (LOL)
     */
    public void shuffleCards() {
        for(int i = 0; i < 12; i++)
            Collections.shuffle(cardPile);
    }

    void printDeck(){
        for(int i = cardPile.size()-1; i >= 0; i--)
            System.out.println(cardPile.get(i));
    }
}
