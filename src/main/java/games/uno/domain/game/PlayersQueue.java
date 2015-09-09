package games.uno.domain.game;

import games.uno.domain.game.Player;

import java.util.List;

public class PlayersQueue {
    private List<Player> players;
    private Direction direction = Direction.CLOCKWISE;
    private int index = 0;

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Player currentPlayer() {
        return getPlayerByIndex(index);
    }

    private Player getPlayerByIndex(int currentPlayerIndex) {
        return players.get(currentPlayerIndex);
    }

    public Player nextPlayer() {
        return getPlayerByIndex(nextTurnIndex());
    }

    private int nextTurnIndex() {
        return direction.nextTurnIndex(index, players.size());
    }

    public void nextTurn() {
        index = nextTurnIndex();
    }

    public void changeDirection() {
        direction = direction == Direction.CLOCKWISE
                ? Direction.COUNTERCLOCKWISE : Direction.CLOCKWISE;
    }

    private enum Direction {
        CLOCKWISE {
            @Override
            int nextTurnIndex(int index, int size) {
                if (++index == size)
                    return 0;
                else
                    return index;
            }
        },

        COUNTERCLOCKWISE {
            @Override
            int nextTurnIndex(int index, int size) {
                if (index == 0)
                    return --size;
                else
                    return --index;
            }
        };

        abstract int nextTurnIndex(int index, int size);
    }
}
