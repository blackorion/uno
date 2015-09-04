package games.uno;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CardTest
{
    @Test
    public void sameTypeAndColorCardsAreEqual() {
        Card card1 = new Card(CardValues.ONE_STEP_BACK, CardColors.BLUE);
        Card card2 = new Card(CardValues.ONE_STEP_BACK, CardColors.BLUE);

        assertEquals(card1, card2);
    }
}