package games.uno.domain.game;

import games.uno.domain.cards.Card;

import java.util.List;

public interface GameMaster {
    void nextPlayer();

    Player currentPlayer();

    Card currentPlayedCard();

    void flipACard();

    void changeDirection();

    void setState(GameState state);

    void persuadePlayerToPlay();

    void setPlayerFinishedMove();

    void start();

    void stop();

    Card drawCard();

    List<Card> drawCard(int times);

    void putInPlayDeck(Card card);

    void flushDeckAndPill();

    void flushPlayersHand();

    GameState state();

    void giveEachPlayerCards(int number);

    boolean isPlayerShouldPlay();

    boolean deckIsEmpty();

    void updateDeckFromPill();

    int deckRemains();

    void shuffleDeck();

    GameTable getTable();

    Card deckFirstCardToDraw();

    boolean didPlayerDrewThisTurn();

    Card lastDrawnCard();

    void finishRound();
}
