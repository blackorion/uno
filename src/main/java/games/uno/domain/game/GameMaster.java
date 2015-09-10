package games.uno.domain.game;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardHolder;

public interface GameMaster
{
    void endTurn();

    Player currentPlayer();

    Card currentPlayedCard();

    void flipACard();

    void playerDrawsFromDeck();

    void changeDirection();

    void setState(GameState state);

    void setPlayerShouldMove();

    CardHolder bankDeck();

    void setPlayerFinishedMove();

    void start();

    void stop();

    void drawCard();

    void playA(Card card);

    void flush();

    void eachPlayer(EachPlayerAction drawSevenCards);

    GameState state();
}
