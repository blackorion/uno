package games.cardgame.core;

import games.cardgame.player.Player;
import games.cardgame.utils.BidirectionalQueue;
import games.uno.domain.cards.UnoCard;
import games.uno.domain.game.GameState;

import java.util.List;

public interface CardGame {
    void start();

    void finish();

    void endTurn();

    void addPlayer(Player player);

    Player currentPlayer();

    UnoCard currentCard();

    UnoCard playerDrawsFromDeck();

    int bankRemains();

    List<Player> players();

    int playersSize();

    void playerPlaysA(UnoCard card);

    BidirectionalQueue.Direction getDirection();

    GameState state();

    UnoCard lastDrawnCard();

    void removePlayers();
}
