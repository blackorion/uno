package games.uno.domain.game;

import games.uno.domain.cards.Card;

public interface Game
{
    void addPlayer(Player player);

    void start();

    Player getCurrentPlayer();

    void endTurn();

    void finish();

    boolean hasPlayers();

    void flush();

    void playerPlaysA(Card card);
}
