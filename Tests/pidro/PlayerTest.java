package pidro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pidro.deck.card.Card;
import pidro.deck.card.CardValue;
import pidro.deck.card.Suit;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player p;

    @BeforeEach
    void setUp() {
        this.p = new Player("testPlayer");
    }

    @Test
    void giveCard() {
        p.giveCard(new Card(Suit.DIAMONDS, CardValue.FIVE));
        p.giveCard(new Card(Suit.HEARTS, CardValue.ACE));

        assertEquals("[five of diamonds, ace of hearts]", p.cardsAsString());
    }

    @Test
    void removeIrrelevantCards() {
        p.giveCard(new Card(Suit.DIAMONDS, CardValue.FIVE));
        p.giveCard(new Card(Suit.HEARTS, CardValue.ACE));
        p.removeIrrelevantCards(Suit.HEARTS);

        //Both cards are kept as the five of diamons is the same color
        assertEquals("[five of diamonds, ace of hearts]", p.cardsAsString());

        p.giveCard(new Card(Suit.CLUBS, CardValue.FIVE));
        p.giveCard(new Card(Suit.SPADES, CardValue.ACE));
        p.removeIrrelevantCards(Suit.SPADES);

        //Should remove both red cards
        assertEquals("[five of clubs, ace of spades]", p.cardsAsString());
    }

    @Test
    void sortCardsBySuit() {

        p.giveCard(new Card(Suit.HEARTS, CardValue.ACE));
        p.giveCard(new Card(Suit.CLUBS, CardValue.FIVE));
        p.giveCard(new Card(Suit.SPADES, CardValue.ACE));
        p.giveCard(new Card(Suit.SPADES, CardValue.EIGHT));
        p.giveCard(new Card(Suit.DIAMONDS, CardValue.FIVE));

        p.sortCardsBySuit();

        //Diamonds, clubs, hearts, spades and by value
        assertEquals("[five of diamonds, five of clubs, ace of hearts, eight of spades, ace of spades]", p.cardsAsString());
    }

    @Test
    void needCards() {
        p.giveCard(new Card(Suit.DIAMONDS, CardValue.FIVE));
        p.giveCard(new Card(Suit.DIAMONDS, CardValue.FOUR));
        p.giveCard(new Card(Suit.DIAMONDS, CardValue.THREE));
        p.giveCard(new Card(Suit.CLUBS, CardValue.FIVE));
        p.giveCard(new Card(Suit.DIAMONDS, CardValue.TWO));

        assertEquals(1, p.neededCards());
    }

    @Test
    void testToString() {
        assertEquals("testPlayer", p.toString());
    }
}