package pidro.deck.card;

import java.util.Comparator;

public class Card implements Comparable<Card> {

    private final CardValue value;

    private final Suit suit;

    public Card(Suit suit, CardValue val) {
        this.suit = suit;
        this.value = val;
    }


    public Suit getSuit() {
        return suit;
    }

    public CardValue getValue() {
        return value;
    }

    /**
     * Determines whether the current card is the out of suit five that should still be kept
     * @param chosenSuit The suit to be played,
     * @return true if this card is a five in of the same color as the current suit
     */
    public boolean isRightColorPidro(Suit chosenSuit) {
        return this.getSuit().getColor() == chosenSuit.getColor() && this.getValue() == CardValue.FIVE;
    }

    /**
     * Sorts firstly by suit in order: Diamonds, Clubs, Hearts, Spades and secondly by value in ascending order
     * @param card card to compareTo
     * @return equal lower or higher
     */
    @Override
    public int compareTo(Card card) {
        Suit otherCardSuit = card.getSuit();
        if (otherCardSuit != this.suit) {
            if (this.suit == Suit.DIAMONDS)
                return -1;
            if (this.suit == Suit.CLUBS)
                return otherCardSuit == Suit.DIAMONDS ? 1 : -1;
            if (this.suit == Suit.HEARTS)
                return otherCardSuit == Suit.SPADES ? -1 : 0;
            if (this.suit == Suit.SPADES)
                return 1;
        }

        //If the cards getting compared are of same suit
        if(this.value.intValue() > card.getValue().intValue())
            return 1;
        else if (this.value.intValue() < card.getValue().intValue())
            return -1;
        return 0; //Should never happen as there can't be two identical cards
    }

    @Override
    public String toString() {
        return String.format("%s of %s",value.toString().toLowerCase(), suit.toString().toLowerCase());
    }
}
