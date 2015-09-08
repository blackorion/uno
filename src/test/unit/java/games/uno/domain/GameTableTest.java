package games.uno.domain;

import games.uno.exceptions.PlayerAlreadyInTheGameException;
import games.uno.exceptions.PlayerLimitForGameException;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class GameTableTest {
    private final Uno gameMock = Mockito.mock(Uno.class);
    GameTable table = new GameTable(gameMock);

    @Test(expected = PlayerLimitForGameException.class)
    public void MaxPlayersCapacity() {
        when(gameMock.state()).thenReturn(Uno.GameState.STOPPED);

        for (int i = 0; i < 16; i++)
            table.add(new Player("Player" + i));
    }

    @Test(expected = PlayerAlreadyInTheGameException.class)
    public void SameUserEntersGameTwice() {
        when(gameMock.state()).thenReturn(Uno.GameState.STOPPED);
        Player player = new Player("player");

        table.add(player);
        table.add(player);
    }

    @Test(expected = IllegalArgumentException.class)
    public void PlayerIsForbiddenJoinTableWhileGameRunning() {
        when(gameMock.state()).thenReturn(Uno.GameState.RUNNING);
        table.add(new Player("player"));
    }
}