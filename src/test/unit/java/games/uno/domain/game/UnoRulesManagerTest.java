package games.uno.domain.game;

import games.uno.exceptions.WrongMoveException;
import games.uno.testutils.NonRandomDeckFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class UnoRulesManagerTest
{
    private GameMaster mockController = Mockito.mock(GameMaster.class);
    private RulesManager manager = new UnoRulesManager(mockController);

    @Before
    public void setUp() throws Exception {
        when(mockController.currentPlayedCard()).thenReturn(NonRandomDeckFactory.ONE_RED);
    }

    @Test
    public void OnGameStart_FirstCardFromBankDeckMovedToPlayedDeck() {
        manager.gameStarted();

        verify(mockController, times(1)).flipACard();
    }

    @Test
    public void OnGameStart_EachUserGets7CardsFromDeck() {
        manager.gameStarted();

        verify(mockController, times(7)).playerDrawsFromDeck();
    }

    @Test(expected = WrongMoveException.class)
    public void PlayCard_CardNotPlayable_ThrowsException() {
        manager.cardPlayed(NonRandomDeckFactory.TWO_BLUE);
    }

    @Test
    public void PlayCard_Playable_MoveCardFromPlayerToPlayDeck() {
        when(mockController.currentPlayedCard()).thenReturn(NonRandomDeckFactory.ONE_RED);

        manager.cardPlayed(NonRandomDeckFactory.ONE_RED);

        verify(mockController, times(1)).playA(NonRandomDeckFactory.ONE_RED);
    }

    @Test
    public void PlayCard_Playable_CurrentPlayerIsMarkedAsCanFinishHisTurn() {
        manager.cardPlayed(NonRandomDeckFactory.TWO_RED);

        verify(mockController, times(1)).setPlayerFinishedMove();
    }

    @Test
    public void PlayCard_Playable_TurnEnds() {
        manager.cardPlayed(NonRandomDeckFactory.ONE_RED);

        verify(mockController, times(1)).endTurn();
    }

    @Test
    public void PlayCard_ReverseCard_ChangesGameDirection() {
        manager.cardPlayed(NonRandomDeckFactory.REVERSE_RED);

        verify(mockController, times(1)).changeDirection();
    }

    @Test
    public void PlayCard_SkipCard_NextPlayerSkipsTurn() {
        manager.cardPlayed(NonRandomDeckFactory.SKIP_RED);

        verify(mockController, times(2)).endTurn();
    }

    @Test
    public void PlayCard_DrawTwoCard_NextPlayerDrawsTwoCardsAndSkipsTurn() {
        manager.cardPlayed(NonRandomDeckFactory.DRAW_TWO_RED);

        verify(mockController, times(2)).drawCard();
        verify(mockController, times(2)).endTurn();
    }

    @Test
    public void GameEnded_FlushesToDefaults() {
        manager.gameStopped();

        verify(mockController, times(1)).flush();
    }

    @Test
    public void PlayerDrawsACard_CanPlayTheDrawnCard() {
        when(mockController.currentPlayedCard()).thenReturn(NonRandomDeckFactory.EIGHT_RED);
        manager.playerDraws();

        manager.cardPlayed(NonRandomDeckFactory.ONE_RED);
    }

    @Test(expected = WrongMoveException.class)
    @Ignore
    public void PlayerDrawsACard_CanPlayOtherThanDrawnCard() {

    }
}