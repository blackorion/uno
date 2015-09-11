package games.uno.domain.game;

import games.uno.domain.cards.Card;

public interface CardGame {
    void start();

    void finish();

    void endTurn();

    void addPlayer(Player player);

    Player currentPlayer();

    Card currentCard();

    Card playerDrawsFromDeck();

    int playersSize();

    boolean hasPlayers();

    void playerPlaysA(Card card);
}
