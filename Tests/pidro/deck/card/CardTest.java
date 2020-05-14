package pidro.deck.card;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    private static Card c1, c2, c3, c4, c5;

    @BeforeAll
    static void init() {
        c1 = new Card(Suit.DIAMONDS, CardValue.FIVE);
        c2 = new Card(Suit.CLUBS, CardValue.ACE);
        c3 = new Card(Suit.SPADES, CardValue.TWO);
        c4 = new Card(Suit.HEARTS, CardValue.KING);
        c5 = new Card(Suit.HEARTS, CardValue.TWO);
    }


    @Test
    void getSuitValue() {
        assertEquals(Suit.DIAMONDS, c1.getSuit());
        assertEquals(5, c1.getValue().intValue());

        assertEquals(Suit.CLUBS, c2.getSuit());
        assertEquals(14, c2.getValue().intValue());

        assertEquals(Suit.SPADES, c3.getSuit());
        assertEquals(2, c3.getValue().intValue());

        assertEquals(Suit.HEARTS, c4.getSuit());
        assertEquals(13, c4.getValue().intValue());
    }

    @Test
    void isRightColorPidro() {
        assertTrue(c1.isRightColorPidro(Suit.HEARTS));
        assertTrue(c1.isRightColorPidro(Suit.DIAMONDS));

        assertFalse(c1.isRightColorPidro(Suit.SPADES));
        assertFalse(c1.isRightColorPidro(Suit.CLUBS));
    }

    @Test
    void compareTo() {
        assertEquals(-1, c1.compareTo(c2));

        assertEquals(1, c3.compareTo(c2));

        assertEquals(-1, c2.compareTo(c5));

        assertEquals(-1, c4.compareTo(c3));

        assertEquals(1, c4.compareTo(c5));

        assertEquals(-1, c5.compareTo(c4));

        assertEquals(0, c5.compareTo(new Card(Suit.HEARTS, CardValue.TWO)));
    }

    @Test
    void testToString() {

        assertEquals("five of diamonds", c1.toString());
    }
}