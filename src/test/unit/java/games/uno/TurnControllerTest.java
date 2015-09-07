package games.uno;

import games.uno.domain.Player;
import games.uno.util.TurnController;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class TurnControllerTest {
    private final Player PLAYER_ONE = new Player("player1");
    private final Player PLAYER_TWO = new Player("player2");
    private TurnController controller = new TurnController();

    @Test
    public void OnGameStart_PlayerSelectedAsCurrent() {
        addPlayers(PLAYER_ONE);

        assertEquals(PLAYER_ONE, controller.currentPlayer());
    }

    @Test
    public void OnEachTurn_NextPlayerSelected() {
        addPlayers(PLAYER_ONE, PLAYER_TWO);
        controller.nextTurn();

        assertEquals(PLAYER_TWO, controller.currentPlayer());
    }

    @Test
    public void WhenLastPlayerFinishedHisTurn_StartsFromFirstPlayerInList() {
        addPlayers(PLAYER_ONE,PLAYER_TWO);
        controller.nextTurn();
        controller.nextTurn();

        assertEquals(PLAYER_ONE, controller.currentPlayer());
    }

    @Test
    public void ShouldReturnNextTurnPlayer() {
        addPlayers(PLAYER_ONE, PLAYER_TWO);

        assertEquals(PLAYER_TWO, controller.nextPlayer());
    }

    private void addPlayers(Player... players) {
        controller.setPlayers(Arrays.asList(players));
    }
}