package games.uno.domain.game;

import games.uno.domain.cards.Card;

public interface RulesManager
{
    void gameStarted();

    void cardPlayed(Card card);

    void gameStopped();

    void playerDraws();

    void entTurn();
}
