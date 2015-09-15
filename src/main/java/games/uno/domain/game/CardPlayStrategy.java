package games.uno.domain.game;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardColors;
import games.uno.exceptions.WrongMoveException;

public class CardPlayStrategy {
    protected final UnoRulesManager rulesManager;
    protected final GameMaster game;

    public CardPlayStrategy(UnoRulesManager rulesManager, GameMaster game) {
        this.rulesManager = rulesManager;
        this.game = game;
    }

    public void play(Card card) {
        verifyMove(card);
        game.putInPlayDeck(card);
        rulesManager.handleCardAction(card);
        game.setPlayerFinishedMove();

        if (game.currentPlayer().cardsOnHand().size() == 0) {
            game.finishRound();
            rulesManager.computeScore();
        }

        rulesManager.endTurn();
    }

    protected void verifyMove(Card card) {
        if (!card.isPlayable(game.currentPlayedCard()))
            throw new WrongMoveException(game.currentPlayedCard(), card);

        if (card.isWild() && card.getColor() == CardColors.DARK)
            throw new WrongMoveException("Color not picked");

        if (game.lastDrawnCard() != null && !card.equals(game.lastDrawnCard()))
            throw new WrongMoveException("Only drawn card is playable");
    }
}
