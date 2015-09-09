package games.uno.domain.game;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.Deck;
import games.uno.exceptions.WrongMoveException;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UnoRulesManagerTest {
    private final Player PLAYER = new Player("PLAYER");
    private Card mockCard = Mockito.mock(Card.class);
    private CardGame gameMock = Mockito.mock(CardGame.class);
    private RulesManager manager = new UnoRulesManager(gameMock);

    @Test
    public void OnGameStart_FirstCardFromBankDeckMovedToPlayedDeck() {
        manager.gameStarted();

        verify(gameMock, times(1)).flipACard();
    }

    @Test
    public void OnGameStart_EachUserGets7CardsFromDeck() {
        when(gameMock.playersSize()).thenReturn(1);
        manager.gameStarted();

        verify(gameMock, times(7)).playerDrawsFromDeck();
    }

    @Test(expected = WrongMoveException.class)
    public void PlayCard_CardNotPlayable_ThrowsException() {
        when(mockCard.isPlayable(any())).thenReturn(false);

        manager.cardPlayed(mockCard);
    }

    @Test
    public void PlayCard_Playable_MoveCardFromPlayerToPlayDeck() {
        when(mockCard.isPlayable(any())).thenReturn(true);
        when(gameMock.getPlayDeck()).thenReturn(new Deck());
        when(gameMock.getCurrentPlayer()).thenReturn(PLAYER);

        manager.cardPlayed(mockCard);

        assertThat(gameMock.getPlayDeck().remains(), is(1));
    }

    @Test
    public void PlayCard_Playable_CurrentPlayerIsMarkedAsCanFinishHisTurn() {
        when(mockCard.isPlayable(any())).thenReturn(true);
        when(gameMock.getCurrentPlayer()).thenReturn(PLAYER);

        manager.cardPlayed(mockCard);

        assertFalse(PLAYER.hasToMakeAMove());
    }
}