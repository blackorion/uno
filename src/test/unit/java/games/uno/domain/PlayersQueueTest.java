package games.uno.domain;

import games.uno.domain.game.Player;
import games.uno.domain.game.PlayersQueue;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class PlayersQueueTest {
    private final Player PLAYER_ONE = new Player("player1");
    private final Player PLAYER_TWO = new Player("player2");
    private final Player PLAYER_THREE = new Player("player3");
    private PlayersQueue controller = new PlayersQueue();

    @Before
    public void setUp() throws Exception {
        addPlayers(PLAYER_ONE, PLAYER_TWO, PLAYER_THREE);
    }

    @Test
    public void OnGameStart_FirstPlayerSelectedAsCurrent() {
        assertEquals(PLAYER_ONE, controller.currentPlayer());
    }

    @Test
    public void OnEachTurn_NextPlayerSelected() {
        controller.nextTurn();

        assertEquals(PLAYER_TWO, controller.currentPlayer());
    }

    @Test
    public void ShouldReturnNextTurnPlayer() {
        assertEquals(PLAYER_TWO, controller.nextPlayer());
    }

    @Test
    public void WhenLastPlayerFinishedHisTurn_StartsFromFirstPlayerInList() {
        controller.nextTurn();
        controller.nextTurn();
        controller.nextTurn();

        assertEquals(PLAYER_ONE, controller.currentPlayer());
    }

    @Test
    public void CounterClockwiseDirectionOnFirsPlayer_OnNextTurnLastOneSelected() {
        controller.changeDirection();

        assertThat(controller.nextPlayer(), is(PLAYER_THREE));
    }

    @Test
    public void CounterClockwiseChangeDirection_PreviousPlayerSelected() {
        controller.nextTurn();
        controller.changeDirection();

        assertThat(controller.nextPlayer(), is(PLAYER_ONE));
    }

    private void addPlayers(Player... players) {
        controller.setPlayers(Arrays.asList(players));
    }
}