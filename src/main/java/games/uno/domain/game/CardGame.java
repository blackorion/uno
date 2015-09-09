package games.uno.domain.game;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.Deck;

public interface CardGame
{
    void addPlayer(Player player);

    void start();

    Card currentPlayedCard();

    Player getCurrentPlayer();

    void endTurn();

    void finish();

    void playerDrawsFromDeck();

    int playersSize();

    boolean hasPlayers();

    void flush();

    void playerPlaysA(Card card);

    void flipACard();

    Deck getPlayDeck();
}
