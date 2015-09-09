package games.uno.web.messages;

import games.uno.domain.cards.Card;
import games.uno.domain.game.BidirectionalQueue.Direction;
import games.uno.domain.game.GameState;

public class GameInfoMessage
{
    private final GameState state;
    private final Direction gameDirection;
    private final int playersInGame;
    private final long currentPlayerId;
    private final Card topCard;
    private final int cardsInBank;

    public GameInfoMessage(GameState state, int playersInGame, long currentPlayerId, Card topCard, int cardsInBank, Direction direction) {
        this.state = state;
        this.playersInGame = playersInGame;
        this.currentPlayerId = currentPlayerId;
        this.topCard = topCard;
        this.cardsInBank = cardsInBank;
        this.gameDirection = direction;
    }

    public GameState getState() { return state; }

    public int getPlayersInGame() { return playersInGame; }

    public long getCurrentPlayerId() { return currentPlayerId; }

    public Card getTopCard() { return topCard; }

    public int getCardsInBank() { return cardsInBank; }

    public Direction getGameDirection() {
        return gameDirection;
    }
}
