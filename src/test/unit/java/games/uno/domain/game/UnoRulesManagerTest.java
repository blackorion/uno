package games.uno.domain.game;

import games.uno.exceptions.IllegalTurnEndException;
import games.uno.exceptions.WrongMoveException;
import games.uno.testutils.NonRandomDeckFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class UnoRulesManagerTest {
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

        verify(mockController, times(1)).giveEachPlayerCards(7);
    }

    @Test(expected = WrongMoveException.class)
    public void PlayCard_CardNotPlayable_ThrowsException() {
        manager.cardPlayed(NonRandomDeckFactory.TWO_BLUE);
    }

    @Test
    public void PlayCard_Playable_MoveCardFromPlayerToPlayDeck() {
        when(mockController.currentPlayedCard()).thenReturn(NonRandomDeckFactory.ONE_RED);

        manager.cardPlayed(NonRandomDeckFactory.ONE_RED);

        verify(mockController, times(1)).putInPlayDeck(NonRandomDeckFactory.ONE_RED);
    }

    @Test
    public void PlayCard_Playable_CurrentPlayerIsMarkedAsCanFinishHisTurn() {
        manager.cardPlayed(NonRandomDeckFactory.TWO_RED);

        verify(mockController, times(1)).setPlayerFinishedMove();
    }

    @Test
    public void PlayCard_Playable_TurnEnds() {
        manager.cardPlayed(NonRandomDeckFactory.ONE_RED);

        verify(mockController, times(1)).nextPlayer();
    }

    @Test
    public void PlayCard_ReverseCard_ChangesGameDirection() {
        manager.cardPlayed(NonRandomDeckFactory.REVERSE_RED);

        verify(mockController, times(1)).changeDirection();
    }

    @Test
    public void PlayCard_SkipCard_NextPlayerSkipsTurn() {
        manager.cardPlayed(NonRandomDeckFactory.SKIP_RED);

        verify(mockController, times(2)).nextPlayer();
    }

    @Test
    public void PlayCard_DrawTwoCard_NextPlayerDrawsTwoCardsAndSkipsTurn() {
        manager.cardPlayed(NonRandomDeckFactory.DRAW_TWO_RED);

        verify(mockController, times(2)).drawCard();
        verify(mockController, times(2)).nextPlayer();
    }

    @Test(expected = WrongMoveException.class)
    public void PlayCard_CardWithOtherThenSelectedColorOnWildCard_ThrownException() {
        when(mockController.currentPlayedCard()).thenReturn(NonRandomDeckFactory.WILD_RED);
        manager.cardPlayed(NonRandomDeckFactory.ONE_BLUE);
    }

    @Test
    public void PlayCard_WildCardOnWildCard_ValidMove() {
        when(mockController.currentPlayedCard()).thenReturn(NonRandomDeckFactory.WILD_RED);
        manager.cardPlayed(NonRandomDeckFactory.WILD_BLUE);
    }

    @Test
    public void PlayCard_WithSelectedColorOnWildCard_ValidMove() {
        manager.cardPlayed(NonRandomDeckFactory.WILD_RED);
        manager.cardPlayed(NonRandomDeckFactory.ONE_RED);
    }

    @Test(expected = WrongMoveException.class)
    public void PlayCard_WildWithoutColorSelected_ThrownException() {
        when(mockController.currentPlayedCard()).thenReturn(NonRandomDeckFactory.ONE_RED);

        manager.cardPlayed(NonRandomDeckFactory.WILD_DARK);
    }

    @Test
    public void PlayerDrawsACard_CanPlayTheDrawnCard() {
        when(mockController.drawCard()).thenReturn(NonRandomDeckFactory.ONE_RED);
        manager.playerDraws();

        manager.cardPlayed(NonRandomDeckFactory.ONE_RED);
    }

    @Test(expected = WrongMoveException.class)
    public void PlayerDrawsACard_PlaysOtherThanDrawnCard_ThrownException() {
        when(mockController.drawCard()).thenReturn(NonRandomDeckFactory.FIVE_RED);
        manager.playerDraws();

        manager.cardPlayed(NonRandomDeckFactory.ONE_RED);
    }

    @Test
    public void PlayerDrawsCard_DeckIsEmpty_PillShuffledToNewDeck() {
        when(mockController.deckIsEmpty()).thenReturn(true);

        manager.playerDraws();

        verify(mockController, times(1)).updateDeckFromPill();
    }

    @Test(expected = WrongMoveException.class)
    public void PlayerDrawsACard_Twice_ThrowsException() {
        when(mockController.drawCard()).thenReturn(NonRandomDeckFactory.FIVE_RED);
        manager.playerDraws();
        manager.playerDraws();
    }

    @Test
    public void PlaysACard_AfterPreviousPlayerDrawn_CanPlayAnyPlayableCard() {
        when(mockController.drawCard()).thenReturn(NonRandomDeckFactory.FIVE_RED);
        manager.playerDraws();
        manager.entTurn();
        manager.cardPlayed(NonRandomDeckFactory.ONE_RED);
    }

    @Test(expected = IllegalTurnEndException.class)
    public void TurnEnd_PlayerHasNotMoved_ThrowsError() {
        when(mockController.isPlayerShouldPlay()).thenReturn(true);

        manager.entTurn();
    }

    @Test
    public void TurnEnd_PlayerPlayed_MoveTurnToNextPlayerAndPersuadeHimToPlay() {
        when(mockController.isPlayerShouldPlay()).thenReturn(false);
        manager.entTurn();

        verify(mockController, times(1)).nextPlayer();
        verify(mockController, times(1)).persuadePlayerToPlay();
    }

    @Test
    public void GameEnded_FlushesToDefaults() {
        manager.gameStopped();

        verify(mockController, times(1)).flush();
    }
}