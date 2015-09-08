package games.uno;

import games.uno.domain.Card;
import games.uno.domain.CardColors;
import games.uno.domain.CardValues;
import games.uno.domain.Deck;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.is;

public class DeckTest
{
    private Deck deck = new Deck();
    private final Card CARD_ONE_RED = new Card(CardValues.ONE, CardColors.RED);
    private final Card CARD_ONE_BLUE = new Card(CardValues.ONE, CardColors.BLUE);
    private final Card CARD_ONE_GREEN = new Card(CardValues.ONE, CardColors.GREEN);
    private final Card CARD_ONE_YELLOW = new Card(CardValues.ONE, CardColors.YELLOW);

    @Test
    public void shouldInsertCards() {
        deck.add(CARD_ONE_BLUE);
        deck.add(CARD_ONE_RED);

        assertEquals(2, deck.remains());
    }

    @Test
    public void PullCardFromTop_ShowsCardsRemains() {
        deck.add(CARD_ONE_BLUE);
        deck.add(CARD_ONE_RED);
        deck.add(CARD_ONE_GREEN);
        deck.add(CARD_ONE_YELLOW);
        assertThat(deck.remains(), is(4));

        deck.giveACardFromTop();

        assertThat(deck.remains(), is(3));
    }

    @Test
    public void cardCanBePulledFromTop() {
        deck.add(CARD_ONE_YELLOW);
        deck.add(CARD_ONE_GREEN);

        assertEquals(CARD_ONE_YELLOW, deck.giveACardFromTop());
        assertEquals(CARD_ONE_GREEN, deck.giveACardFromTop());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void throwsErrorWhenNoMoreCardsInDeck() {
        deck.add(CARD_ONE_GREEN);

        deck.giveACardFromTop();
        deck.giveACardFromTop();
    }
}