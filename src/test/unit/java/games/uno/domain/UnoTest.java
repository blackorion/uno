package games.uno.domain;

import games.uno.util.DeckFactory;
import games.uno.exceptions.*;
import games.uno.testutils.NonRandomDeckFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class UnoTest
{
    private Uno uno;
    private DeckFactory deckFactoryMock;
    private PlayersQueue playersQueueMock;
    private final Player PLAYER_ONE = new Player("username1");
    private final Player PLAYER_TWO = new Player("username2");

    @Before
    public void setUp() throws Exception {
        deckFactoryMock = Mockito.mock(DeckFactory.class);
        when(deckFactoryMock.generate()).thenReturn(createDeck());
        playersQueueMock = Mockito.mock(PlayersQueue.class);
        uno = new Uno(deckFactoryMock, playersQueueMock);
    }

    @Test(expected = GameAlreadyStartedException.class)
    public void TryingToStartGameThatAlreadyStarted_ThrowsException() {
        uno.addPlayer(PLAYER_ONE);
        uno.start();
        uno.start();
    }

    @Test
    public void shouldGenerateTheDeckAtStart() {
        verify(deckFactoryMock, times(1)).generate();
    }

    @Test(expected = NoUsersInTheGameException.class)
    public void shouldThrowExceptionOnStartIfNoPlayersConnected() {
        uno.start();
    }

    @Test
    public void OnGameStart_EachUserGets7CardsFromDeck() {
        uno.addPlayer(PLAYER_ONE);
        uno.addPlayer(PLAYER_TWO);

        uno.start();

        assertEquals(7, PLAYER_ONE.getCardsOnHand().size());
        assertEquals(7, PLAYER_TWO.getCardsOnHand().size());
    }

    @Test
    public void OnGameStart_FirstCardFromBankDeckMovedToPlayedDeck() {
        uno.addPlayer(PLAYER_ONE);
        uno.start();

        assertEquals(new Card(CardValues.FOUR, CardColors.RED), uno.currentPlayedCard());
    }

    @Test
    public void ForNextTurn_CallsPlayersQueue() {
        when(playersQueueMock.currentPlayer()).thenReturn(PLAYER_ONE);
        uno.addPlayer(PLAYER_ONE);
        uno.addPlayer(PLAYER_TWO);
        uno.start();
        uno.playerPullsFromDeck();
        uno.endTurn();

        verify(playersQueueMock, atLeastOnce()).nextTurn();
    }

    @Test
    public void PlayerCanPlayACardWithSameColorOrValue() {
        when(playersQueueMock.currentPlayer()).thenReturn(PLAYER_ONE);
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
        when(playersQueueMock.currentPlayer()).thenReturn(PLAYER_ONE);
        uno.addPlayer(PLAYER_ONE);
        uno.start();
        uno.playerPuts(new Card(CardValues.FOUR, CardColors.RED));

        verify(playersQueueMock, times(1)).nextTurn();
    }

    @Test(expected = IllegalTurnEndException.class)
    public void CurrentPlayerHasNoCardToPlay_ShouldPullCardFromDeckBeforeEndOfTurn() {
        uno.addPlayer(PLAYER_ONE);
        uno.start();
        uno.endTurn();
    }

    @Test
    public void afterGameHasFinished_ReturnToDefault() {
        uno.addPlayer(PLAYER_ONE);
        uno.start();
        uno.finish();

        assertThat(PLAYER_ONE.remains(), is(0));
        uno.addPlayer(PLAYER_TWO);
    }

    private Deck createDeck() { return new NonRandomDeckFactory().generate(); }
}