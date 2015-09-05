package games.uno;

import java.util.List;

public class TurnController {
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
        int result = index + 1;

        if (result == players.size())
            return 0;
        else
            return result;
    }

    public void nextTurn() {
        index = nextTurnIndex();
    }

    private enum Direction {CLOCKWISE, COUNTERCLOCKWISE}
}
