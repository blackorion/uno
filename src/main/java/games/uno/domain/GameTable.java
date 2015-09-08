package games.uno.domain;

import games.uno.exceptions.PlayerAlreadyInTheGameException;
import games.uno.exceptions.PlayerLimitForGameException;

import java.util.ArrayList;
import java.util.List;

public class GameTable {
    private static final int MAX_PLAYERS = 15;
    private final List<Player> players = new ArrayList<>();
    private Uno game;

    public GameTable(Uno game) {
        this.game = game;
    }

    public void add(Player player) {
        if (players.contains(player))
            throw new PlayerAlreadyInTheGameException(player);

        if (game.state().isRunning())
            throw new IllegalArgumentException("The game has already started.");

        if (players.size() == MAX_PLAYERS)
            throw new PlayerLimitForGameException();

        players.add(player);
    }

    public List<Player> players() {
        return players;
    }

    public boolean isEmpty() {
        return players.size() == 0;
    }
}
