package games.uno.domain;

import games.uno.domain.game.GameState;
import games.uno.domain.game.GameTable;
import games.uno.domain.game.Player;
import games.uno.domain.game.UnoGameFacade;
import games.uno.exceptions.PlayerAlreadyInTheGameException;
import games.uno.exceptions.PlayerLimitForGameException;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class GameTableTest {
    private final UnoGameFacade gameMock = Mockito.mock(UnoGameFacade.class);
    GameTable table = new GameTable(gameMock);

    @Test(expected = PlayerLimitForGameException.class)
    public void MaxPlayersCapacity() {
        when(gameMock.state()).thenReturn(GameState.NOT_RUNNING);

        for (int i = 0; i < 16; i++)
            table.add(new Player("Player" + i));
    }

    @Test(expected = PlayerAlreadyInTheGameException.class)
    public void SameUserEntersGameTwice() {
        when(gameMock.state()).thenReturn(GameState.NOT_RUNNING);
        Player player = new Player("player");

        table.add(player);
        table.add(player);
    }

    @Test(expected = IllegalArgumentException.class)
    public void PlayerIsForbiddenJoinTableWhileGameRunning() {
        when(gameMock.state()).thenReturn(GameState.RUNNING);
        table.add(new Player("player"));
    }
}