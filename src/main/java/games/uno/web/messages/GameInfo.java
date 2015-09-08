package games.uno.web.messages;

import games.uno.domain.Card;

public class GameInfo
{
    private final String state;
    private final int playersInGame;
    private final long currentPlayerId;
    private final Card topCard;
    private final int cardsInBank;

    public GameInfo(String state, int playersInGame, long currentPlayerId, Card topCard, int cardsInBank) {
        this.state = state;
        this.playersInGame = playersInGame;
        this.currentPlayerId = currentPlayerId;
        this.topCard = topCard;
        this.cardsInBank = cardsInBank;
    }

    public String getState() { return state; }

    public int getPlayersInGame() { return playersInGame; }

    public long getCurrentPlayerId() { return currentPlayerId; }

    public Card getTopCard() { return topCard; }

    public int getCardsInBank() { return cardsInBank; }
}
