package games.uno;

import games.uno.exceptions.NoUsersInTheGameException;
import games.uno.utils.NonRandomDeckFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class UnoTest
{
    private Uno uno;
    private DeckFactory deckFactoryMock;
    private TurnController turnControllerMock;
    private final Player PLAYER_ONE = new Player("username1");
    private final Player PLAYER_TWO = new Player("username2");

    @Before
    public void setUp() throws Exception {
        deckFactoryMock = Mockito.mock(DeckFactory.class);
        when(deckFactoryMock.generate()).thenReturn(createDeck());
        turnControllerMock = Mockito.mock(TurnController.class);
        uno = new Uno(deckFactoryMock, turnControllerMock);
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
        uno.addPlayer(PLAYER_ONE);
        uno.start();
        uno.addPlayer(PLAYER_TWO);
    }

    @Test
    public void OnEachTurn_NextPlayerSelected() {
        when(turnControllerMock.currentPlayer()).thenReturn(PLAYER_ONE);
        uno.addPlayer(PLAYER_ONE);
        uno.addPlayer(PLAYER_TWO);
        uno.start();
        uno.playerPullsFromDeck();
        uno.endTurn();

        verify(turnControllerMock, atLeastOnce()).nextTurn();
    }

    @Test
    public void PlayerCanPlayACardWithSameColorOrValue() {
        when(turnControllerMock.currentPlayer()).thenReturn(PLAYER_ONE);
        uno.addPlayer(PLAYER_ONE);
        uno.start();
        uno.playerPuts(new Card(CardValues.FOUR, CardColors.BLUE));
        uno.playerPuts(new Card(CardValues.ONE, CardColors.BLUE));
    }

    @Test(expected = WrongMoveException.class)
    public void PlayerCantPlayACardOtherColorOrValue() {
        uno.addPlayer(PLAYER_ONE);
        uno.start();
        uno.playerPuts(new Card(CardValues.ONE, CardColors.BLUE));
    }

    @Test
    public void WhenPlayerPlaysACard_TurnGoesToNextPlayer() {
        when(turnControllerMock.currentPlayer()).thenReturn(PLAYER_ONE);
        uno.addPlayer(PLAYER_ONE);
        uno.start();
        uno.playerPuts(new Card(CardValues.FOUR, CardColors.RED));

        verify(turnControllerMock, times(1)).nextTurn();
    }

    @Test(expected = IllegalTurnEndException.class)
    public void CurrentPlayerHasNoCardToPlay_ShouldPullCardFromDeckBeforeEndOfTurn() {
        uno.addPlayer(PLAYER_ONE);
        uno.start();
        uno.endTurn();
    }

    private Deck createDeck() { return new NonRandomDeckFactory().generate(); }
}