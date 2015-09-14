package games.uno.domain.game;

import games.uno.domain.cards.Card;

import java.util.List;

public interface CardGame {
    void start();

    void finish();

    void endTurn();

    void addPlayer(Player player);

    Player currentPlayer();

    Card currentCard();

    Card playerDrawsFromDeck();

    int bankRemains();

    List<Player> players();

    int playersSize();

    void playerPlaysA(Card card);

    BidirectionalQueue.Direction getDirection();

    GameState state();

    Card lastDrawnCard();
}
