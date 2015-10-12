package games.humanresources;

import games.cardgame.player.Player;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class LobbyTests {
    @Test
    public void Join_NewUserNoRooms_JoinsFirstCreatedRoom() {
        Lobby lobby = createLobbyWithOneEmptyRoom();

        lobby.join(new Player("player"));

        assertNotNull(lobby.getRoom(0));
        assertThat(lobby.getRoom(0).playersInRoom(), is(1));
    }

    @Test
    public void Join_NewUserHalfFullRoomExists_JoinsThatRoom() {
        Lobby lobby = createLobbyWithRoomAndPlayersIn(5);

        lobby.join(new Player("player"));

        assertNotNull(lobby.getRoom(0));
        assertThat(lobby.getRoom(0).playersInRoom(), is(6));
    }

    @Test
    public void Join_NewUserFullRoomExists_JoinsNewCreatedRoom() {
        Lobby lobby = createLobbyWithRoomAndPlayersIn(15);

        lobby.join(new Player("player"));

        assertNotNull(lobby.getRoom(1));
        assertThat(lobby.getRoom(1).playersInRoom(), is(1));
    }

    private Lobby createLobbyWithRoomAndPlayersIn(int players) {
        Lobby lobby = createLobbyWithOneEmptyRoom();

        for (int i = 0; i < players; i++)
            lobby.join(new Player("player" + i));

        return lobby;
    }

    private Lobby createLobbyWithOneEmptyRoom() {
        return new Lobby();
    }
}
