package games.uno.domain.game;

import games.uno.exceptions.PlayerAlreadyInTheGameException;
import games.uno.exceptions.PlayerLimitForGameException;
import java.util.List;

public class GameTable
{
    private static final int MAX_PLAYERS = 15;
    private final BidirectionalQueue<Player> queue = new BidirectionalQueue<>();
    private final UnoGameFacade game;

    public GameTable(UnoGameFacade game) {
        this.game = game;
    }

    public void add(Player player) {
        validatePlayerCanJoin(player);
        queue.offer(player);
    }

    private void validatePlayerCanJoin(Player player) {
        if ( queue.contains(player) )
            throw new PlayerAlreadyInTheGameException(player);

        if ( game.state().isRunning() )
            throw new IllegalArgumentException("The game has already started.");

        if ( queue.size() == MAX_PLAYERS )
            throw new PlayerLimitForGameException();
    }

    public List<Player> players() {
        return queue.getElements();
    }

    public boolean isEmpty() {
        return queue.size() == 0;
    }

    public BidirectionalQueue.Direction getDirection() {
        return queue.getDirection();
    }

    public Player currentPlayer() {
        if ( game.state() == GameState.NOT_RUNNING )
            return players().get(0);

        return queue.peek();
    }

    public void nextTurn() {
        queue.next();
    }

    public void changeDirection() {
        queue.changeDirection();
    }
}
