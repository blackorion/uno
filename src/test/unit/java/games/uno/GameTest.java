package games.uno;

import games.uno.utils.NonRandomDeckFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameTest extends Assert
{
    private Uno uno;

    @Before
    public void setUp() throws Exception {
        uno = new Uno(new NonRandomDeckFactory());
    }

    @Test
    public void WhenTheGameStarts_EachUserGets7CardsFromDeck() {
        Player player1 = new Player("username");
        Player player2 = new Player("username");
        uno.addPlayer(player1);
        uno.addPlayer(player2);

        uno.start();

        assertEquals(7, player1.getCardsOnHand().size());
        assertEquals(7, player2.getCardsOnHand().size());
    }

    @Test
    public void WhenGameStarts_FirstCardFromDeckMovedToPlayedDeck() {
        uno.addPlayer(new Player("player"));
        uno.start();

        assertEquals(new Card(CardValues.FOUR, CardColors.RED), uno.currentPlayedCard());
    }
}
