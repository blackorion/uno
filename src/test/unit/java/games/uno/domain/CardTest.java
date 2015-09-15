package games.uno.domain;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardColors;
import games.uno.domain.cards.CardValues;
import games.uno.testutils.NonRandomDeckFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {
    @Test
    public void sameTypeAndColorCardsAreEqual() {
        Card card1 = new Card(CardValues.REVERSE, CardColors.BLUE);
        Card card2 = new Card(CardValues.REVERSE, CardColors.BLUE);

        assertEquals(card1, card2);
    }

    @Test
    public void sameTypeWildCardsAreEqualWithDarkColoredCard() {
        assertEquals(NonRandomDeckFactory.WILD_BLUE, NonRandomDeckFactory.WILD_DARK);
    }

    @Test
    public void NotPlayableOnNull() {
        assertFalse(new Card(CardValues.ONE, CardColors.RED).isPlayable(null));
    }

    @Test
    public void DarkCardIsPlayableOnAnyCard() {
        assertTrue(NonRandomDeckFactory.WILD_RED.isPlayable(NonRandomDeckFactory.ONE_RED));
        assertTrue(NonRandomDeckFactory.WILD_BLUE.isPlayable(NonRandomDeckFactory.EIGHT_RED));
        assertTrue(NonRandomDeckFactory.WILD_DRAW_FOUR_BLUE.isPlayable(NonRandomDeckFactory.EIGHT_RED));
    }

    @Test
    public void isPlayable() {
        assertTrue(NonRandomDeckFactory.REVERSE_RED.isPlayable(NonRandomDeckFactory.WILD_RED));
    }

    @Test
    public void Score() {
        assertEquals(0, NonRandomDeckFactory.ZERO_RED.getScore());
        assertEquals(1, NonRandomDeckFactory.ONE_RED.getScore());
        assertEquals(2, NonRandomDeckFactory.TWO_BLUE.getScore());
        assertEquals(3, NonRandomDeckFactory.THREE_BLUE.getScore());
        assertEquals(4, NonRandomDeckFactory.FOUR_RED.getScore());
        assertEquals(5, NonRandomDeckFactory.FIVE_RED.getScore());
        assertEquals(6, NonRandomDeckFactory.SIX_RED.getScore());
        assertEquals(7, NonRandomDeckFactory.SEVEN_RED.getScore());
        assertEquals(8, NonRandomDeckFactory.EIGHT_RED.getScore());
        assertEquals(9, NonRandomDeckFactory.NINE_RED.getScore());
    }
}