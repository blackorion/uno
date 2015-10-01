package games.cardgame.core;

import games.uno.domain.cards.UnoCard;

public interface RulesManager
{
    void gameStarted();

    void cardPlayed(UnoCard card);

    void gameStopped();

    UnoCard playerDraws();

    void endTurn();
}
