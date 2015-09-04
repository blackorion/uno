package games.uno;

import games.uno.exceptions.NoUsersInTheGameException;
import games.uno.utils.NonRandomDeckFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;

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
    public void shouldThrowExceptionOnStartIfNoPlayersConnected() {
        uno.start();
    }

    @Test(expected = IllegalArgumentException.class)
    public void AfterGameStarted_PlayerCantAccess() {
        uno.addPlayer(new Player("username"));
        uno.start();
        uno.addPlayer(new Player("username"));
    }

    @Test
    public void OnGameStart_PlayerSelectedAsCurrent() {
        Player player = new Player("username");
        uno.addPlayer(player);
        uno.start();

        assertEquals(player, uno.getCurrentPlayer());
    }

    @Test
    public void OnEachTurn_NextPlayerSelected() {
        Player player1 = new Player("username1");
        Player player2 = new Player("username2");
        uno.addPlayer(player1);
        uno.addPlayer(player2);
        uno.start();
        uno.endTurn();

        assertEquals(player2, uno.getCurrentPlayer());
    }

    @Test
    public void WhenLastPlayerFinishedHisTurn_StartsFromFirstPlayerInList() {
        Player player1 = new Player("username1");
        Player player2 = new Player("username2");
        uno.addPlayer(player1);
        uno.addPlayer(player2);
        uno.start();
        uno.endTurn();
        uno.endTurn();

        assertEquals(player1, uno.getCurrentPlayer());
    }

    @Test
    public void NextPlayer_ReturnsPlayerWhosTurnIsNext() {
        Player player1 = new Player("username1");
        Player player2 = new Player("username2");
        uno.addPlayer(player1);
        uno.addPlayer(player2);
        uno.start();

        assertEquals(player2, uno.getNextPlayer());
    }

    private Deck createDeck() { return new NonRandomDeckFactory().generate(); }
}