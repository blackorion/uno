package games.uno.domain;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardColors;
import games.uno.domain.cards.CardValues;
import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest
{
    @Test
    public void sameTypeAndColorCardsAreEqual() {
        Card card1 = new Card(CardValues.REVERSE, CardColors.BLUE);
        Card card2 = new Card(CardValues.REVERSE, CardColors.BLUE);

        assertEquals(card1, card2);
    }

    @Test
    public void NotPlayableOnNull() {
        assertFalse(new Card(CardValues.ONE, CardColors.RED).isPlayable(null));
    }

    @Test
    public void DarkCardIsPlayableOnAnyCard() {
        assertTrue(new Card(CardValues.WILD, CardColors.DARK).isPlayable(new Card(CardValues.ONE, CardColors.RED)));
        assertTrue(new Card(CardValues.WILD_DRAW_FOUR, CardColors.DARK).isPlayable(new Card(CardValues.ONE, CardColors.RED)));
    }
}