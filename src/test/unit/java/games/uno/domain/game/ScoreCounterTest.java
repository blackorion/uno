package games.uno.domain.game;

import games.uno.domain.cards.Deck;
import games.uno.exceptions.WonPlayerDetermineException;
import games.uno.testutils.NonRandomDeckFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScoreCounterTest {
    private final Player PLAYER_ONE = new Player("player1");
    private final Player PLAYER_TWO = new Player("player2");
    private final GameTable table = new GameTable(new UnoGameMaster(new Deck(), new Deck()));
    private final ScoreCounter counter = new ScoreCounter(table);

    @Before
    public void setUp() {
        table.add(PLAYER_ONE);
        table.add(PLAYER_TWO);
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