package games.uno.domain;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardColors;
import games.uno.domain.cards.CardValues;
import games.uno.domain.cards.Deck;
import games.uno.domain.game.Player;
import games.uno.domain.game.Uno;
import games.uno.exceptions.GameAlreadyStartedException;
import games.uno.exceptions.IllegalTurnEndException;
import games.uno.exceptions.NoUsersInTheGameException;
import games.uno.exceptions.WrongMoveException;
import games.uno.testutils.NonRandomDeckFactory;
import games.uno.util.DeckFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class UnoTest
{
    private Uno uno;
    private DeckFactory deckFactoryMock;
    private final Player PLAYER_ONE = new Player("username1");
    private final Player PLAYER_TWO = new Player("username2");

    @Before
    public void setUp() throws Exception {
        deckFactoryMock = Mockito.mock(DeckFactory.class);
        when(deckFactoryMock.generate()).thenReturn(createDeck());
        uno = new Uno(deckFactoryMock);
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

        assertThat(uno.getCurrentPlayer(), is(PLAYER_ONE));
    }

    @Test(expected = NoUsersInTheGameException.class)
    public void ShouldThrowExceptionOnStartIfNoPlayersConnected() {
        uno.start();
    }

    @Test
    public void WhenPlayerPlaysACard_TurnGoesToNextPlayer() {
        uno.addPlayer(PLAYER_ONE);
        uno.addPlayer(PLAYER_TWO);
        uno.start();
        uno.playerPlaysA(new Card(CardValues.FOUR, CardColors.RED));

        assertThat(uno.getCurrentPlayer(), is(PLAYER_TWO));
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

        assertFalse(PLAYER_ONE.hasToMakeAMove());
    }

    @Test
    public void PlayerDrawsACard_CanPlayTheDrawedCard() {
        uno.addPlayer(PLAYER_ONE);
        uno.start();
        uno.playerDrawsFromDeck();

        uno.playerPlaysA(new Card(CardValues.FIVE, CardColors.RED));
    }

    @Test(expected = WrongMoveException.class)
    @Ignore
    public void PlayerDrawsACard_CanPlayOtherThanDrawedCard() {
        uno.addPlayer(PLAYER_ONE);
        uno.start();
        uno.playerDrawsFromDeck();

        uno.playerPlaysA(new Card(CardValues.SEVEN, CardColors.BLUE));
    }

    @Test
    public void AfterGameHasFinished_ReturnToDefaults() {
        uno.addPlayer(PLAYER_ONE);
        uno.start();
        uno.finish();

        assertThat(PLAYER_ONE.remains(), is(0));
        uno.addPlayer(PLAYER_TWO);
    }

    private Deck createDeck() { return new NonRandomDeckFactory().generate(); }
}