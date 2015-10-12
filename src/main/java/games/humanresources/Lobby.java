package games.humanresources;

import games.cardgame.core.GameTable;
import games.cardgame.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private final List<Room> rooms = new ArrayList<>();

    public void join(Player player) {
        Room room = getCurrentRoom();
        room.join(player);
    }

    private Room getCurrentRoom() {
        if (rooms.isEmpty()) {
            return createNewRoom();
        }

        if (lastRoom().playersInRoom() < GameTable.MAX_PLAYERS_CAPABILITY)
            return lastRoom();
        else
            return createNewRoom();
    }

    private Room lastRoom() {
        return rooms.get(rooms.size() - 1);
    }

    private Room createNewRoom() {
        Room room = new Room();
        rooms.add(room);

        return room;
    }

    public List<Room> rooms() {
        return rooms;
    }

    public Room getRoom(int index) {
        return rooms.get(index);
    }
}
