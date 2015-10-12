package games.humanresources;

import games.cardgame.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private List<Player> players = new ArrayList<>();

    public void join(Player player) {
        players.add(player);
        player.setRoom(this);
    }

    public int playersInRoom() {
        return players.size();
    }

    public void remove(Player player) {
        players.remove(player);
        player.setRoom(null);
    }
}
