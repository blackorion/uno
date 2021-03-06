package games.cardgame.core;

import games.cardgame.exceptions.PlayerAlreadyInTheGameException;
import games.cardgame.exceptions.PlayerLimitForGameException;
import games.cardgame.player.Player;
import games.cardgame.utils.BidirectionalQueue;
import games.uno.domain.game.GameState;

import java.util.List;

public class GameTable {
    public static final int MAX_PLAYERS_CAPABILITY = 15;
    private final BidirectionalQueue<Player> queue = new BidirectionalQueue<>();
    private final GameMaster game;

    public GameTable(GameMaster game) {
        this.game = game;
    }

    public void add(Player player) {
        validatePlayerCanJoin(player);
        queue.offer(player);
    }

    private void validatePlayerCanJoin(Player player) {
        if (queue.contains(player))
            throw new PlayerAlreadyInTheGameException(player);

        if (game.state().isRunning())
            throw new IllegalArgumentException("The game has already started.");

        if (queue.size() == MAX_PLAYERS_CAPABILITY)
            throw new PlayerLimitForGameException();
    }

    public List<Player> players() {
        return queue.getElements();
    }

    public BidirectionalQueue.Direction getDirection() {
        return queue.getDirection();
    }

    public Player currentPlayer() {
        if (game.state() == GameState.NOT_RUNNING)
            return players().get(0);

        return queue.peek();
    }

    public void nextTurn() {
        queue.next();
    }

    public void changeDirection() {
        queue.changeDirection();
    }

    public void removePlayers() {
        queue.clear();
    }
}
