package games.uno.domain;

import games.uno.domain.game.*;
import games.uno.exceptions.MinimumUsersForTheGameException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class BasicCardGameTest {
    private BasicCardGame uno;
    private final Player PLAYER_ONE = new Player("username1");
    private final Player PLAYER_TWO = new Player("username2");
    private GameMaster gameMaster;
    private RulesManager rulesManager;
    private GameTable table;

    @Before
    public void setUp() throws Exception {
        gameMaster = Mockito.mock(GameMaster.class);
        table = Mockito.mock(GameTable.class);
        when(gameMaster.getTable()).thenReturn(table);
        rulesManager = Mockito.mock(RulesManager.class);
        uno = new BasicCardGame(gameMaster, rulesManager);
    }

    @Test
    public void AddPlayer_AnyPlayer_AddedToGame() {
        uno.addPlayer(PLAYER_ONE);

        verify(table, times(1)).add(PLAYER_ONE);
    }

    @Test(expected = MinimumUsersForTheGameException.class)
    public void Start_LessThanTwoPlayers_ThrowsException() {
        createPlayers(PLAYER_ONE);

        uno.start();
    }

    private void createPlayers(Player... players) {
        when(table.players()).thenReturn(Arrays.asList(players));
    }
}