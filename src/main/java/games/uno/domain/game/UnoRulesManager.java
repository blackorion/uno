package games.uno.domain.game;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardColors;
import games.uno.domain.cards.CardValues;
import games.uno.exceptions.IllegalTurnEndException;
import games.uno.exceptions.WrongMoveException;

public class UnoRulesManager implements RulesManager {
    private final GameMaster game;
    private boolean firstCardIsWild = false;

    public UnoRulesManager(GameMaster game) {
        this.game = game;
    }

    @Override
    public void gameStarted() {
        game.start();
        game.giveEachPlayerCards(7);
        handleTopCardAction();
        game.flipACard();
        game.persuadePlayerToPlay();
    }

    private void handleTopCardAction() {
        while (game.deckFirstCardToDraw().getValue() == CardValues.WILD_DRAW_FOUR)
            game.shuffleDeck();

        if (game.deckFirstCardToDraw().getValue() == CardValues.WILD) {
            firstCardIsWild = true;
            game.drawCard();
        }

        handleCardAction(game.deckFirstCardToDraw());

        if (game.deckFirstCardToDraw().getValue() == CardValues.REVERSE) {
            game.nextPlayer();
        }
    }

    @Override
    public void cardPlayed(Card card) {
        verifyValidMove(card);
        game.putInPlayDeck(card);
        handleCardAction(card);

        if (firstCardIsWild) {
            firstCardIsWild = false;
            return;
        }

        game.setPlayerFinishedMove();
        endTurn();
    }

    private void verifyValidMove(Card card) {
        if (!card.isPlayable(game.currentPlayedCard()))
            throw new WrongMoveException(game.currentPlayedCard(), card);

        if (card.isWild() && card.getColor() == CardColors.DARK)
            throw new WrongMoveException("Color not picked");

        if (game.lastDrawnCard() != null && !card.equals(game.lastDrawnCard()))
            throw new WrongMoveException("Only drawn card is playable");
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
        game.stop();
        game.flushDeckAndPill();
        game.flushPlayersHand();
    }

    @Override
    public Card playerDraws() {
        if (game.didPlayerDrewThisTurn())
            throw new WrongMoveException("Can't draw more than one card in turn.");

        Card lastDrawnCard = game.drawCard();
        game.setPlayerFinishedMove();

        if (game.deckIsEmpty())
            game.updateDeckFromPill();

        if (drawnCardNotPlayable(lastDrawnCard))
            endTurn();

        return game.lastDrawnCard();
    }

    private boolean drawnCardNotPlayable(Card card) {
        return card != null && !card.isPlayable(game.currentPlayedCard());
    }

    @Override
    public void endTurn() {
        if (game.isPlayerShouldPlay())
            throw new IllegalTurnEndException();

        game.nextPlayer();
        game.persuadePlayerToPlay();
    }
}
