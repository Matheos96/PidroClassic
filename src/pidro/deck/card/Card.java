package pidro.deck.card;

import java.util.Comparator;

public class Card implements Comparable<Card> {

    private final CardValue value;

    private final Suit suit;

    public static Comparator<Card> cardSortComparator = (Card c1, Card c2) -> {
        int val1 = c1.getSuit().compareTo(c2.getSuit());
        if (val1 == 0) {
            return c1.compareTo(c2);
        }
        return val1;
    };

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
    public boolean isOutOfSuitPidro(Suit chosenSuit) {
        return this.getSuit().getColor() == chosenSuit.getColor() && this.getValue() == CardValue.FIVE;
    }

    @Override
    public int compareTo(Card card) {
        if(this.value.intValue() > card.getValue().intValue())
            return 1;
        else if (this.value.intValue() < card.getValue().intValue())
            return -1;
        return 0;
    }

    @Override
    public String toString() {
        return String.format("%s of %s",value.toString().toLowerCase(), suit.toString().toLowerCase());
    }
}
