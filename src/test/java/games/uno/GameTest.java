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
        User user1 = new User("username");
        User user2 = new User("username");
        uno.addUser(user1);
        uno.addUser(user2);

        uno.start();

        assertEquals(7, user1.getCards().size());
        assertEquals(7, user2.getCards().size());
    }

    @Test
    public void WhenGameStarts_FirstCardFromDeckMovedToPlayedDeck() {
        uno.addUser(new User("user"));
        uno.start();

        assertEquals(new Card(CardValues.FOUR, CardColors.RED), uno.currentPlayedCard());
    }
}
