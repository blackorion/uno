package games.uno.domain.game;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardColors;
import games.uno.domain.cards.CardValues;
import games.uno.exceptions.IllegalTurnEndException;
import games.uno.exceptions.WrongMoveException;

public class UnoRulesManager implements RulesManager {
    private final GameMaster game;
    private boolean firstCardIsWild = false;
    private Card lastDrawnCard;

    public UnoRulesManager(GameMaster game) {
        this.game = game;
    }

    @Override
    public void gameStarted() {
        game.start();
        game.giveEachPlayerCards(7);
        game.flipACard();
        handleFirstCardAction();
    }

    private void handleFirstCardAction() {
        while (game.currentPlayedCard().getValue() == CardValues.WILD_DRAW_FOUR) {
            game.returnCardFromPillToDeck();
            game.shuffleDeck();
            game.flipACard();
        }

        if (game.currentPlayedCard().getValue() == CardValues.WILD) {
            game.returnCardFromPillToDeck();
            lastDrawnCard = game.drawCard();
            firstCardIsWild = true;
        }

        handleCardAction(game.currentPlayedCard());

        if (game.currentPlayedCard().getValue() == CardValues.REVERSE)
            game.nextPlayer();
    }

    @Override
    public void cardPlayed(Card card) {
        if (!card.isPlayable(game.currentPlayedCard()))
            throw new WrongMoveException(game.currentPlayedCard(), card);

        if (card.isWild() && card.getColor() == CardColors.DARK)
            throw new WrongMoveException("Color not picked");

        if (lastDrawnCard != null && !card.equals(lastDrawnCard))
            throw new WrongMoveException("Only drawn card is playable");

        game.putInPlayDeck(card);
        handleCardAction(card);

        if (firstCardIsWild) {
            firstCardIsWild = false;
            return;
        }

        game.setPlayerFinishedMove();
        endTurn();
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
        } else if (card.getValue() == CardValues.WILD_DRAW_FOUR) {
            game.nextPlayer();
            game.drawCard();
            game.drawCard();
            game.drawCard();
            game.drawCard();
        }
    }

    @Override
    public void gameStopped() {
        lastDrawnCard = null;
        game.stop();
        game.flushDeckAndPill();
        game.flushPlayersHand();
    }

    @Override
    public Card playerDraws() {
        if (lastDrawnCard != null)
            throw new WrongMoveException("Can't draw more than one card in turn.");

        lastDrawnCard = game.drawCard();
        game.setPlayerFinishedMove();

        if (game.deckIsEmpty())
            game.updateDeckFromPill();

        if (noCardsToMakeMove())
            endTurn();

        return lastDrawnCard;
    }

    private boolean noCardsToMakeMove() {
        for (Card card : game.currentPlayer().cardsOnHand())
            if (card.isPlayable(game.currentPlayedCard()))
                return false;

        return true;
    }

    @Override
    public void endTurn() {
        if (game.isPlayerShouldPlay())
            throw new IllegalTurnEndException();

        game.nextPlayer();
        game.persuadePlayerToPlay();
        lastDrawnCard = null;
    }

}
