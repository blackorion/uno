package games.uno.domain;

import games.uno.domain.cards.Deck;
import games.uno.domain.game.Player;
import games.uno.domain.game.UnoGameFacade;
import games.uno.exceptions.GameAlreadyStartedException;
import games.uno.exceptions.IllegalTurnEndException;
import games.uno.exceptions.NoUsersInTheGameException;
import games.uno.testutils.NonRandomDeckFactory;
import games.uno.util.DeckFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertFalse;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class UnoGameFacadeTest
{
    private UnoGameFacade uno;
    private DeckFactory deckFactoryMock;
    private final Player PLAYER_ONE = new Player("username1");
    private final Player PLAYER_TWO = new Player("username2");

    @Before
    public void setUp() throws Exception {
        deckFactoryMock = Mockito.mock(DeckFactory.class);
        when(deckFactoryMock.generate()).thenReturn(createDeck());
        uno = new UnoGameFacade(deckFactoryMock);
    }

    @Test(expected = GameAlreadyStartedException.class)
    public void TryingToStartGameThatAlreadyStarted_ThrowsException() {
        uno.addPlayer(PLAYER_ONE);
        uno.start();
        uno.start();
    }

    @Test
    public void ShouldGenerateTheDeckAtStart() {
        verify(deckFactoryMock, times(1)).generate();
    }

    @Test
    public void CurrentPlayer_GameNotStarted_ReturnsFirstPlayer() {
        uno.addPlayer(PLAYER_ONE);

        assertThat(uno.currentPlayer(), is(PLAYER_ONE));
    }

    @Test(expected = NoUsersInTheGameException.class)
    public void ShouldThrowExceptionOnStartIfNoPlayersConnected() {
        uno.start();
    }

    @Test(expected = IllegalTurnEndException.class)
    public void CurrentPlayerHasNoCardToPlay_ShouldPullCardFromDeckBeforeEndOfTurn() {
        uno.addPlayer(PLAYER_ONE);
        uno.start();
        uno.endTurn();
    }

    @Test
    public void PlayerPullsACardFromDeck_PlayerCanFinishHisMove() {
        uno.addPlayer(PLAYER_ONE);
        uno.start();
        uno.playerDrawsFromDeck();

        assertFalse(PLAYER_ONE.isShouldPlay());
    }

    private Deck createDeck() { return new NonRandomDeckFactory().generate(); }
}