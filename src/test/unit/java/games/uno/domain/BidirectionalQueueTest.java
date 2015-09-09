package games.uno.domain;

import games.uno.domain.game.BidirectionalQueue;
import games.uno.domain.game.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;

public class BidirectionalQueueTest
{
    private final Player PLAYER_ONE = new Player("player1");
    private final Player PLAYER_TWO = new Player("player2");
    private final Player PLAYER_THREE = new Player("player3");
    private BidirectionalQueue queue = new BidirectionalQueue();

    @Before
    public void setUp() throws Exception {
        addPlayers(PLAYER_ONE, PLAYER_TWO, PLAYER_THREE);
    }

    @Test
    public void OnGameStart_FirstPlayerSelectedAsCurrent() {
        assertEquals(PLAYER_ONE, queue.peek());
    }

    @Test
    public void OnEachTurn_NextPlayerSelected() {
        queue.next();

        assertEquals(PLAYER_TWO, queue.peek());
    }

    @Test
    public void ShouldReturnNextTurnPlayer() {
        assertEquals(PLAYER_TWO, queue.peekNext());
    }

    @Test
    public void WhenLastPlayerFinishedHisTurn_StartsFromFirstPlayerInList() {
        queue.next();
        queue.next();
        queue.next();

        assertEquals(PLAYER_ONE, queue.peek());
    }

    @Test
    public void CounterClockwiseDirectionOnFirsPlayer_OnNextTurnLastOneSelected() {
        queue.changeDirection();

        assertThat(queue.peekNext(), is(PLAYER_THREE));
    }

    @Test
    public void CounterClockwiseChangeDirection_PreviousPlayerSelected() {
        queue.next();
        queue.changeDirection();

        assertThat(queue.peekNext(), is(PLAYER_ONE));
    }

    private void addPlayers(Player... players) {
        queue.setElements(Arrays.asList(players));
    }
}