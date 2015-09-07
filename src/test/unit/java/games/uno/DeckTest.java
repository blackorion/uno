package games.uno;

import games.uno.domain.Card;
import games.uno.domain.CardColors;
import games.uno.domain.CardValues;
import games.uno.domain.Deck;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DeckTest
{
    @Test
    public void shouldInsertCards() {
        Deck deck = new Deck();

        deck.add(new Card(CardValues.ONE_STEP_BACK, CardColors.BLUE));
        deck.add(new Card(CardValues.ONE_STEP_BACK, CardColors.RED));

        assertEquals(2, deck.remains());
    }

    @Test
    public void cardCanBePulledFromTop() {
        Deck deck = new Deck();
        Card expectedCard1 = new Card(CardValues.ONE_STEP_BACK, CardColors.BLUE);
        Card expectedCard2 = new Card(CardValues.TAKE_TWO, CardColors.BLUE);
        deck.add(expectedCard1);
        deck.add(expectedCard2);

        assertEquals(expectedCard1, deck.giveACardFromTop());
        assertEquals(expectedCard2, deck.giveACardFromTop());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void throwsErrorWhenNoMoreCardsInDeck() {
        Deck deck = new Deck();
        deck.add(new Card(CardValues.TAKE_TWO, CardColors.DARK));

        deck.giveACardFromTop();
        deck.giveACardFromTop();
    }
}