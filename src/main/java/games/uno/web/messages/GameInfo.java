package games.uno.web.messages;

public class GameInfo
{
    private final String state;
    private final int playersInGame;

    public GameInfo(String state, int playersInGame) {
        this.state = state;
        this.playersInGame = playersInGame;
    }

    public String getState() {
        return state;
    }

    public int getPlayersInGame() {
        return playersInGame;
    }
}
