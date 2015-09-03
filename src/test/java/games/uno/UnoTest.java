package games.uno;

import games.uno.exceptions.NoUsersInTheGameException;
import games.uno.utils.NonRandomDeckFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class UnoTest
{
    private Uno uno;
    private DeckFactory deckFactoryMock;

    @Before
    public void setUp() throws Exception {
        deckFactoryMock = Mockito.mock(DeckFactory.class);
        when(deckFactoryMock.generate()).thenReturn(createDeck());
        uno = new Uno(deckFactoryMock);
    }

    @Test
    public void shouldGenerateTheDeckAtStart() {
        verify(deckFactoryMock, times(1)).generate();
    }

    @Test(expected = NoUsersInTheGameException.class)
    public void shouldThrowExceptionOnStartIfNoUsersConnected() {
        uno.start();
    }

    @Test(expected = IllegalArgumentException.class)
    public void AfterGameStarted_UserCantAccess() {
        uno.addUser(new User("username"));
        uno.start();
        uno.addUser(new User("username"));
    }

    private Deck createDeck() {
        return new NonRandomDeckFactory().generate();
    }
}