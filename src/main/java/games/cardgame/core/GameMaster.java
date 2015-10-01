package games.cardgame.core;

import games.cardgame.player.Player;
import games.uno.domain.cards.UnoCard;
import games.uno.domain.game.GameState;

import java.util.List;

public interface GameMaster {
    void nextPlayer();

    Player currentPlayer();

    UnoCard currentPlayedCard();

    void flipACard();

    void changeDirection();

    void setState(GameState state);

    void persuadePlayerToPlay();

    void setPlayerFinishedMove();

    void start();

    void stop();

    UnoCard drawCard();

    List<UnoCard> drawCard(int times);

    void moveToPill(UnoCard card);

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

    UnoCard deckFirstCardToDraw();

    boolean didPlayerDrewThisTurn();

    UnoCard lastDrawnCard();

    void finishRound();

    int cardsInPill();
}
