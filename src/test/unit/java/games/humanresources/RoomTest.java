package games.humanresources;

import games.cardgame.player.Player;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class RoomTest {
    @Test
    public void WhenPlayerJoinsTheRoom_RoomLinkStoredInPlayerObject() {
        Player player = new Player("player");
        Room room = new Room();

        room.join(player);

        assertThat(player.getRoom(), is(room));
    }

    @Test
    public void WhenPlayerLeavesRoom_LinkToRoomRemovedFromPlayerObject() {
        Player player = new Player("player");
        Room room = new Room();

        room.join(player);
        room.remove(player);

        assertThat(player.getRoom(), is(nullValue()));
    }
}