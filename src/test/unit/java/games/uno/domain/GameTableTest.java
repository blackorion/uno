package games.uno.domain;

import games.uno.domain.game.GameMaster;
import games.uno.domain.game.GameState;
import games.uno.domain.game.GameTable;
import games.uno.domain.game.Player;
import games.uno.exceptions.PlayerAlreadyInTheGameException;
import games.uno.exceptions.PlayerLimitForGameException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class GameTableTest {
    private GameMaster gameMock = Mockito.mock(GameMaster.class);
    private GameTable table = new GameTable(gameMock);

    @Before
    public void setUp() {
        when(gameMock.state()).thenReturn(GameState.NOT_RUNNING);
    }

    @Test(expected = PlayerLimitForGameException.class)
    public void Add_MoreThanMaxPlayersCapacity_ThrowsException() {
        for (int i = 0; i < 16; i++)
            table.add(new Player("Player" + i));
    }

    @Test(expected = PlayerAlreadyInTheGameException.class)
    public void Add_SameUserEntersGameTwice_ThrowsException() {
        Player player = new Player("player");

        table.add(player);
        table.add(player);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Add_GameIsRunning_Forbidden() {
        when(gameMock.state()).thenReturn(GameState.RUNNING);

        table.add(new Player("player"));
    }

    @Test
    public void CurrentPlayer_GameNotStarted_ReturnsFirstPlayer() {
        Player expectedPlayer = new Player("player");

        table.add(expectedPlayer);

        assertThat(table.currentPlayer(), is(expectedPlayer));
    }

    @Test
    public void RemovePlayers_All_TableIsEmpty() {
        table.add(new Player("player"));
        table.add(new Player("player2"));
        table.add(new Player("player3"));

        table.removePlayers();

        assertTrue(table.players().isEmpty());
    }
}