package games.uno;

import games.uno.domain.Card;
import games.uno.domain.CardColors;
import games.uno.domain.CardValues;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
}