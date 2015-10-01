package games.uno.domain.game;

import games.cardgame.core.GameTable;
import games.cardgame.core.ScoreCounter;
import games.cardgame.exceptions.WonPlayerDetermineException;
import games.cardgame.player.Player;
import games.cardgame.testutils.NonRandomDeckFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class UnoScoreCounterTest {
    private final Player PLAYER_ONE = new Player("player1");
    private final Player PLAYER_TWO = new Player("player2");
    private final GameTable stubTable = Mockito.mock(GameTable.class);
    private final ScoreCounter counter = new ScoreCounter(stubTable);

    @Before
    public void setUp() {
        when(stubTable.currentPlayer())
                .thenReturn(PLAYER_ONE);
        when(stubTable.players()).thenReturn(Arrays.asList(PLAYER_ONE, PLAYER_TWO));
    }

    @Test
    public void Compute_NoCards_ZeroPoints() {
        counter.compute();

        assertEquals(0, PLAYER_ONE.getGameScore());
        assertEquals(0, PLAYER_TWO.getGameScore());
    }

    @Test
    public void Compute_NumericCards_CardsValue() {
        PLAYER_ONE.take(NonRandomDeckFactory.FIVE_RED);

        counter.compute();

        assertEquals(5, PLAYER_TWO.getGameScore());
    }

    @Test
    public void Compute_ActionCards_20PointsEach() {
        PLAYER_ONE.take(NonRandomDeckFactory.REVERSE_RED);
        PLAYER_ONE.take(NonRandomDeckFactory.DRAW_TWO_RED);

        counter.compute();

        assertEquals(40, PLAYER_TWO.getGameScore());
    }

    @Test
    public void Compute_WildCards_50PointsEach() {
        PLAYER_ONE.take(NonRandomDeckFactory.WILD_BLUE);
        PLAYER_ONE.take(NonRandomDeckFactory.WILD_DARK);

        counter.compute();

        assertEquals(100, PLAYER_TWO.getGameScore());
    }

    @Test
    public void Compute_NextTurn_ScoreAddedToPrevious() {
        PLAYER_ONE.take(NonRandomDeckFactory.ONE_BLUE);
        PLAYER_ONE.take(NonRandomDeckFactory.ONE_RED);
        counter.compute();

        assertEquals(2, PLAYER_TWO.getGameScore());

        PLAYER_ONE.dropAll();
        counter.compute();

        assertEquals(2, PLAYER_TWO.getGameScore());

        PLAYER_ONE.dropAll();
        PLAYER_ONE.take(NonRandomDeckFactory.ONE_RED);
        counter.compute();

        assertEquals(3, PLAYER_TWO.getGameScore());

        PLAYER_ONE.dropAll();
        PLAYER_TWO.take(NonRandomDeckFactory.FIVE_RED);
        counter.compute();

        assertEquals(5, PLAYER_ONE.getGameScore());
    }

    @Test(expected = WonPlayerDetermineException.class)
    public void Compute_AllPlayersHaveScore_ThrowException() {
        PLAYER_ONE.take(NonRandomDeckFactory.ONE_BLUE);
        PLAYER_TWO.take(NonRandomDeckFactory.ONE_RED);

        counter.compute();
    }
}