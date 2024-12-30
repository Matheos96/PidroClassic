package pidroclassic.deck.card;

import java.io.Serializable;

public enum Suit implements Serializable { HEARTS(Color.RED),
    DIAMONDS(Color.RED),
    SPADES(Color.BLACK),
    CLUBS(Color.BLACK)
    ;

    private final Color color;

    Suit(Color c) {
        this.color = c;
    }

    public Color getColor() {
        return color;
    }


    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
