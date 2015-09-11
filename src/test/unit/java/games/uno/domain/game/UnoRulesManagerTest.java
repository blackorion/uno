package games.uno.domain.game;

import games.uno.domain.cards.Card;
import games.uno.exceptions.IllegalTurnEndException;
import games.uno.exceptions.WrongMoveException;
import games.uno.testutils.NonRandomDeckFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class UnoRulesManagerTest {
    private GameMaster mockController = Mockito.mock(GameMaster.class);
    private RulesManager manager = new UnoRulesManager(mockController);

    @Before
    public void setUp() throws Exception {
        when(mockController.currentPlayedCard()).thenReturn(NonRandomDeckFactory.ONE_RED);
        createPlayer();
    }

    @Test
    public void GameStart_FirstCardFromBankDeckMovedToPlayedDeck() {
        manager.gameStarted();

        verify(mockController, times(1)).flipACard();
    }

    @Test
    public void GameStart_EachUserGets7CardsFromDeck() {
        manager.gameStarted();

        verify(mockController, times(1)).giveEachPlayerCards(7);
    }

    @Test
    public void GameStart_SkipCard_FirstPlayerLosesTurn() {
        when(mockController.currentPlayedCard()).thenReturn(NonRandomDeckFactory.SKIP_RED);

        manager.gameStarted();

        verify(mockController, times(1)).nextPlayer();
    }

    @Test
    public void GameStart_DrawTwoCard_FirstPlayerTakesTwoCardsAndLosesTurn() {
        when(mockController.currentPlayedCard()).thenReturn(NonRandomDeckFactory.DRAW_TWO_RED);

        manager.gameStarted();

        verify(mockController, times(1)).nextPlayer();
        verify(mockController, times(2)).drawCard();
    }

    @Test
    public void GameStart_ReverseCard_GameChangesDirectionFirstPlayerLosesTurn() {
        when(mockController.currentPlayedCard()).thenReturn(NonRandomDeckFactory.REVERSE_RED);

        manager.gameStarted();

        verify(mockController, times(1)).changeDirection();
        verify(mockController, times(1)).nextPlayer();
    }

    @Test
    public void GameStart_WildCard_PlayerChoosesColorAndContinuesHisTurn() {
        when(mockController.currentPlayedCard())
                .thenReturn(NonRandomDeckFactory.WILD_DARK)
                .thenReturn(NonRandomDeckFactory.WILD_BLUE);

        manager.gameStarted();
        manager.cardPlayed(NonRandomDeckFactory.WILD_BLUE);
        manager.cardPlayed(NonRandomDeckFactory.ONE_BLUE);

        verify(mockController, times(1)).returnCardFromPillToDeck();
        verify(mockController, times(1)).drawCard();
        verify(mockController, times(1)).putInPlayDeck(NonRandomDeckFactory.WILD_BLUE);
        verify(mockController, times(1)).putInPlayDeck(NonRandomDeckFactory.ONE_BLUE);
        verify(mockController, times(1)).setPlayerFinishedMove();
        verify(mockController, times(1)).nextPlayer();
    }

    @Test
    public void GameStart_WildDrawFourCard_ReturnedToDeckAndShuffled() {
        when(mockController.currentPlayedCard())
                .thenReturn(NonRandomDeckFactory.WILD_DRAW_FOUR_DARK)
                .thenReturn(NonRandomDeckFactory.WILD_DRAW_FOUR_DARK)
                .thenReturn(NonRandomDeckFactory.ONE_RED);

        manager.gameStarted();

        verify(mockController, times(2)).returnCardFromPillToDeck();
        verify(mockController, times(2)).shuffleDeck();
        verify(mockController, times(3)).flipACard();
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
    public void PlayCard_ReverseCard_PersuadeNextPlayerToPlay() {
        manager.cardPlayed(NonRandomDeckFactory.REVERSE_RED);

        verify(mockController, times(1)).persuadePlayerToPlay();
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

    @Test
    public void PlayCard_WildDrawFourCard_NextPlayerDrawsFourCardsAndSkips() {
        manager.cardPlayed(NonRandomDeckFactory.WILD_DRAW_FOUR_BLUE);

        verify(mockController, times(4)).drawCard();
        verify(mockController, times(2)).nextPlayer();
    }

    @Test(expected = WrongMoveException.class)
    public void PlayCard_OtherThenSelectedColorOnWildCard_ThrownException() {
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
    public void PlayCard_DrawnWild_ShouldBePlayable() {
        createPlayer(NonRandomDeckFactory.WILD_DARK);
        when(mockController.drawCard()).thenReturn(NonRandomDeckFactory.WILD_DARK);
        manager.playerDraws();

        manager.cardPlayed(NonRandomDeckFactory.WILD_BLUE);
    }

    @Test
    public void DrawCard_CanPlayTheDrawnCard() {
        when(mockController.drawCard()).thenReturn(NonRandomDeckFactory.ONE_RED);
        manager.playerDraws();

        manager.cardPlayed(NonRandomDeckFactory.ONE_RED);
    }

    @Test(expected = WrongMoveException.class)
    public void DrawCard_PlaysOtherThanDrawnCard_ThrownException() {
        createPlayer(NonRandomDeckFactory.ONE_RED, NonRandomDeckFactory.FOUR_RED);
        when(mockController.drawCard()).thenReturn(NonRandomDeckFactory.FIVE_RED);

        manager.playerDraws();

        manager.cardPlayed(NonRandomDeckFactory.ONE_RED);
    }

    @Test
    public void DrawCard_DeckIsEmpty_PillShuffledToNewDeck() {
        when(mockController.deckIsEmpty()).thenReturn(true);

        manager.playerDraws();

        verify(mockController, times(1)).updateDeckFromPill();
    }

    @Test
    public void DrawCard_NoPlayableCards_NextPlayerTurn() {
        createPlayer(NonRandomDeckFactory.TWO_BLUE, NonRandomDeckFactory.THREE_BLUE);

        manager.playerDraws();

        verify(mockController, times(1)).nextPlayer();
    }

    @Test(expected = WrongMoveException.class)
    public void DrawCard_Twice_ThrowsException() {
        createPlayer(NonRandomDeckFactory.ONE_RED, NonRandomDeckFactory.FIVE_RED);
        when(mockController.drawCard()).thenReturn(NonRandomDeckFactory.FIVE_RED);
        manager.playerDraws();

        manager.playerDraws();
    }

    @Test
    public void PlaysACard_AfterPreviousPlayerDrawn_CanPlayAnyPlayableCard() {
        when(mockController.drawCard()).thenReturn(NonRandomDeckFactory.FIVE_RED);
        manager.playerDraws();
        manager.endTurn();

        manager.cardPlayed(NonRandomDeckFactory.ONE_RED);
    }

    @Test(expected = IllegalTurnEndException.class)
    public void TurnEnd_PlayerHasNotMoved_ThrowsError() {
        when(mockController.isPlayerShouldPlay()).thenReturn(true);

        manager.endTurn();
    }

    @Test
    public void TurnEnd_PlayerPlayed_MoveTurnToNextPlayerAndPersuadeHimToPlay() {
        when(mockController.isPlayerShouldPlay()).thenReturn(false);
        manager.endTurn();

        verify(mockController, times(1)).nextPlayer();
        verify(mockController, times(1)).persuadePlayerToPlay();
    }

    @Test
    public void GameEnded_FlushesToDefaults() {
        manager.gameStopped();

        verify(mockController, times(1)).flushDeckAndPill();
    }

    private Player createPlayer(Card... cards) {
        Player player = new Player("player");

        for (Card card : cards)
            player.take(card);

        when(mockController.currentPlayer()).thenReturn(player);

        return player;
    }
}