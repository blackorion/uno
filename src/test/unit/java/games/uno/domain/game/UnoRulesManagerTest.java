package games.uno.domain.game;

import games.cardgame.core.GameMaster;
import games.cardgame.core.RulesManager;
import games.cardgame.core.ScoreCounter;
import games.cardgame.exceptions.IllegalTurnEndException;
import games.cardgame.exceptions.WrongMoveException;
import games.cardgame.player.Player;
import games.cardgame.testutils.NonRandomDeckFactory;
import games.uno.domain.cards.UnoCard;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class UnoRulesManagerTest {
    private ScoreCounter scoreCounter = Mockito.mock(ScoreCounter.class);
    private GameMaster mockController = Mockito.mock(GameMaster.class);
    private RulesManager manager = new UnoRulesManager(mockController, scoreCounter);

    @Before
    public void setUp() throws Exception {
        when(mockController.currentPlayedCard()).thenReturn(NonRandomDeckFactory.ONE_RED);
        createPlayerWithHand();
    }

    @Test
    public void GameStart_FirstCardFromBankDeckMovedToPlayedDeck() {
        when(mockController.deckFirstCardToDraw()).thenReturn(NonRandomDeckFactory.ONE_RED);

        manager.gameStarted();

        verify(mockController, times(1)).flipACard();
    }

    @Test
    public void GameStart_EachUserGets7CardsFromDeck() {
        when(mockController.deckFirstCardToDraw()).thenReturn(NonRandomDeckFactory.ONE_RED);

        manager.gameStarted();

        verify(mockController, times(1)).giveEachPlayerCards(7);
    }

    @Test
    public void GameStart_FirstPlayer_PersuadedToPlay() {
        when(mockController.deckFirstCardToDraw()).thenReturn(NonRandomDeckFactory.ONE_RED);

        manager.gameStarted();

        verify(mockController, atLeastOnce()).persuadePlayerToPlay();
    }

    @Test
    public void GameStart_SkipCard_FirstPlayerLosesTurn() {
        createPlayerWithHand(NonRandomDeckFactory.FIVE_RED, NonRandomDeckFactory.SKIP_RED);
        when(mockController.deckFirstCardToDraw()).thenReturn(NonRandomDeckFactory.SKIP_RED);

        manager.gameStarted();

        verify(mockController, times(1)).nextPlayer();
    }

    @Test
    public void GameStart_DrawTwoCard_FirstPlayerTakesTwoCardsAndLosesTurn() {
        when(mockController.deckFirstCardToDraw()).thenReturn(NonRandomDeckFactory.DRAW_TWO_RED);

        manager.gameStarted();

        verify(mockController, times(1)).nextPlayer();
        verify(mockController, times(1)).drawCard(2);
    }

    @Test
    public void GameStart_ReverseCard_GameChangesDirectionFirstPlayerLosesTurn() {
        when(mockController.deckFirstCardToDraw()).thenReturn(NonRandomDeckFactory.REVERSE_RED);

        manager.gameStarted();

        verify(mockController, times(1)).changeDirection();
        verify(mockController, times(1)).nextPlayer();
    }

    @Test
    public void GameStart_WildCard_PlayerChoosesColorAndContinuesHisTurn() {
        createPlayerWithHand(NonRandomDeckFactory.WILD_RED, NonRandomDeckFactory.WILD_DARK);
        when(mockController.deckFirstCardToDraw())
                .thenReturn(NonRandomDeckFactory.WILD_DARK)
                .thenReturn(NonRandomDeckFactory.WILD_BLUE);

        manager.gameStarted();
        manager.cardPlayed(NonRandomDeckFactory.WILD_BLUE);
        manager.cardPlayed(NonRandomDeckFactory.ONE_BLUE);

        verify(mockController, times(1)).drawCard();
        verify(mockController, times(1)).moveToPill(NonRandomDeckFactory.WILD_BLUE);
        verify(mockController, times(1)).moveToPill(NonRandomDeckFactory.ONE_BLUE);
        verify(mockController, times(1)).setPlayerFinishedMove();
        verify(mockController, times(1)).nextPlayer();
    }

    @Test
    public void GameStart_WildDrawFourCard_ReturnedToDeckAndShuffled() {
        when(mockController.deckFirstCardToDraw())
                .thenReturn(NonRandomDeckFactory.WILD_DRAW_FOUR_DARK)
                .thenReturn(NonRandomDeckFactory.WILD_DRAW_FOUR_DARK)
                .thenReturn(NonRandomDeckFactory.ONE_RED);

        manager.gameStarted();

        verify(mockController, times(2)).shuffleDeck();
        verify(mockController, times(1)).flipACard();
    }

    @Test(expected = WrongMoveException.class)
    public void PlayCard_CardNotPlayable_ThrowsException() {
        manager.cardPlayed(NonRandomDeckFactory.TWO_BLUE);
    }

    @Test
    public void PlayCard_Playable_MoveCardFromPlayerToPlayDeck() {
        when(mockController.currentPlayedCard()).thenReturn(NonRandomDeckFactory.ONE_RED);

        manager.cardPlayed(NonRandomDeckFactory.ONE_RED);

        verify(mockController, times(1)).moveToPill(NonRandomDeckFactory.ONE_RED);
    }

    @Test
    public void PlayCard_Playable_CurrentPlayerIsMarkedAsCanFinishHisTurn() {
        manager.cardPlayed(NonRandomDeckFactory.TWO_RED);

        verify(mockController, times(1)).setPlayerFinishedMove();
    }

    @Test
    public void PlayCard_Playable_TurnEnds() {
        createPlayerWithHand(NonRandomDeckFactory.FIVE_RED);
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
        createPlayerWithHand(NonRandomDeckFactory.ONE_RED, NonRandomDeckFactory.REVERSE_RED);
        manager.cardPlayed(NonRandomDeckFactory.REVERSE_RED);

        verify(mockController, times(1)).persuadePlayerToPlay();
    }

    @Test
    public void PlayCard_SkipCard_NextPlayerSkipsTurn() {
        createPlayerWithHand(NonRandomDeckFactory.FIVE_RED, NonRandomDeckFactory.SKIP_RED);
        manager.cardPlayed(NonRandomDeckFactory.SKIP_RED);

        verify(mockController, times(2)).nextPlayer();
    }

    @Test
    public void PlayCard_DrawTwoCard_NextPlayerDrawsTwoCardsAndSkipsTurn() {
        createPlayerWithHand(NonRandomDeckFactory.FIVE_RED, NonRandomDeckFactory.DRAW_TWO_RED);
        manager.cardPlayed(NonRandomDeckFactory.DRAW_TWO_RED);

        verify(mockController, times(1)).drawCard(2);
        verify(mockController, times(2)).nextPlayer();
    }

    @Test
    public void PlayCard_WildDrawFourCard_NextPlayerDrawsFourCardsAndSkips() {
        createPlayerWithHand(NonRandomDeckFactory.FIVE_RED, NonRandomDeckFactory.WILD_DRAW_FOUR_BLUE);
        manager.cardPlayed(NonRandomDeckFactory.WILD_DRAW_FOUR_BLUE);

        verify(mockController, times(1)).drawCard(4);
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
        createPlayerWithHand(NonRandomDeckFactory.WILD_DARK);
        setDrawnCard(NonRandomDeckFactory.WILD_DARK);
        manager.playerDraws();

        manager.cardPlayed(NonRandomDeckFactory.WILD_BLUE);
    }

    @Test
    public void PlayCard_LastCardIsSkip_TurnDoesNotSkipped() {
        createPlayerWithHand();

        manager.cardPlayed(NonRandomDeckFactory.SKIP_RED);

        verify(mockController, never()).nextPlayer();
        verify(mockController, times(1)).finishRound();
    }

    @Test
    public void PlayCard_LastCard_PlayerWon() {
        createPlayerWithHand();

        manager.cardPlayed(NonRandomDeckFactory.TWO_RED);

        verify(mockController, times(1)).finishRound();
    }

    @Test
    public void PlayCard_RoundFinished_CountScore() {
        createPlayerWithHand();

        manager.cardPlayed(NonRandomDeckFactory.TWO_RED);

        verify(scoreCounter, times(1)).compute();
    }

    @Test
    public void RoundFinish_PlayerReaches500_PlayerWinsTheGame() {
        Player player = createPlayerWithHand();
        player.addScore(500);

        manager.cardPlayed(NonRandomDeckFactory.TWO_RED);

        verify(mockController, times(1)).stop();
    }

    @Test
    public void PlayCard_DrawTwoAndRoundFinished_NextPlayerTakesTwoAndCountScore() {
        createPlayerWithHand();

        manager.cardPlayed(NonRandomDeckFactory.DRAW_TWO_RED);

        verify(scoreCounter, times(1)).compute();
        verify(mockController, times(1)).drawCard(2);
    }

    @Test
    public void PlayCard_DrawFourAndRoundFinished_NextPlayerTakesFourAndCountScore() {
        createPlayerWithHand();

        manager.cardPlayed(NonRandomDeckFactory.WILD_DRAW_FOUR_BLUE);

        verify(scoreCounter, times(1)).compute();
        verify(mockController, times(1)).drawCard(4);
    }

    @Test
    public void DrawCard_CanPlayTheDrawnCard() {
        setDrawnCard(NonRandomDeckFactory.ONE_RED);
        manager.playerDraws();

        manager.cardPlayed(NonRandomDeckFactory.ONE_RED);
    }

    @Test(expected = WrongMoveException.class)
    public void DrawCard_PlaysOtherThanDrawnCard_ThrownException() {
        createPlayerWithHand(NonRandomDeckFactory.ONE_RED, NonRandomDeckFactory.FOUR_RED);
        setDrawnCard(NonRandomDeckFactory.FIVE_RED);

        manager.playerDraws();

        manager.cardPlayed(NonRandomDeckFactory.ONE_RED);
    }

    @Test
    public void DrawCard_AnyCard_CanFinishTurn() {
        createPlayerWithHand(NonRandomDeckFactory.ONE_RED, NonRandomDeckFactory.FIVE_RED);

        manager.playerDraws();

        verify(mockController, atLeastOnce()).setPlayerFinishedMove();
    }

    @Test
    public void DrawCard_NoPlayableCards_NextPlayerTurn() {
        createPlayerWithHand(NonRandomDeckFactory.TWO_BLUE, NonRandomDeckFactory.THREE_BLUE);
        setDrawnCard(NonRandomDeckFactory.TWO_BLUE);

        manager.playerDraws();

        verify(mockController, times(1)).nextPlayer();
    }

    @Test
    public void DrawCard_PlayableCardsOnHandButNotPlayableDrawn_NextPlayerTurn() {
        createPlayerWithHand(NonRandomDeckFactory.ONE_RED, NonRandomDeckFactory.THREE_RED);
        setDrawnCard(NonRandomDeckFactory.TWO_BLUE);

        manager.playerDraws();

        verify(mockController, times(1)).nextPlayer();
    }

    @Test(expected = WrongMoveException.class)
    public void DrawCard_Twice_ThrowsException() {
        when(mockController.didPlayerDrewThisTurn()).thenReturn(true);

        manager.playerDraws();
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

    private Player createPlayerWithHand(UnoCard... cards) {
        Player player = new Player("player");

        for (UnoCard card : cards)
            player.take(card);

        when(mockController.currentPlayer()).thenReturn(player);

        return player;
    }

    private UnoCard setDrawnCard(UnoCard drawn) {
        when(mockController.drawCard()).thenReturn(drawn);
        when(mockController.lastDrawnCard()).thenReturn(drawn);

        return drawn;
    }
}