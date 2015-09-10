package games.uno.domain.game;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardColors;
import games.uno.domain.cards.CardValues;
import games.uno.exceptions.IllegalTurnEndException;
import games.uno.exceptions.WrongMoveException;

public class UnoRulesManager implements RulesManager {
    private final GameMaster game;
    private Card lastDrawnCard;

    public UnoRulesManager(GameMaster game) {
        this.game = game;
    }

    @Override
    public void gameStarted() {
        game.start();
        eachPlayerGetsHand();
        game.flipACard();
    }

    @Override
    public void cardPlayed(Card card) {
        if (!card.isPlayable(game.currentPlayedCard()))
            throw new WrongMoveException(game.currentPlayedCard(), card);

        if (card.isWild() && card.getColor() == CardColors.DARK)
            throw new WrongMoveException("Color not picked");

        if (lastDrawnCard != null && card != lastDrawnCard)
            throw new WrongMoveException(game.currentPlayedCard(), card);

        game.putInPlayDeck(card);
        handleCardAction(card);
        game.setPlayerFinishedMove();
        game.nextPlayer();
    }

    private void handleCardAction(Card card) {
        if (card.getValue() == CardValues.REVERSE)
            game.changeDirection();
        else if (card.getValue() == CardValues.SKIP)
            game.nextPlayer();
        else if (card.getValue() == CardValues.DRAW_TWO) {
            game.nextPlayer();
            game.drawCard();
            game.drawCard();
        }
    }

    @Override
    public void gameStopped() {
        game.stop();
        game.flush();
    }

    @Override
    public void playerDraws() {
        if (lastDrawnCard != null)
            throw new WrongMoveException("Can't draw more than one card in turn.");

        lastDrawnCard = game.drawCard();
        game.setPlayerFinishedMove();

        if (game.deckIsEmpty())
            game.updateDeckFromPill();
    }

    @Override
    public void entTurn() {
        if (game.isPlayerShouldPlay())
            throw new IllegalTurnEndException();

        game.nextPlayer();
        game.persuadePlayerToPlay();
        lastDrawnCard = null;
    }

    private void eachPlayerGetsHand() {
        game.giveEachPlayerCards(7);
    }
}
