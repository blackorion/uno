package games.uno.domain.game;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardColors;
import games.uno.exceptions.WrongMoveException;

abstract class CardPlayStrategy {
    protected final UnoRulesManager rulesManager;
    protected final GameMaster game;

    public CardPlayStrategy(UnoRulesManager rulesManager, GameMaster game) {
        this.rulesManager = rulesManager;
        this.game = game;
    }

    protected void verifyMove(Card card) {
        if (!card.isPlayable(game.currentPlayedCard()))
            throw new WrongMoveException(game.currentPlayedCard(), card);

        if (card.isWild() && card.getColor() == CardColors.DARK)
            throw new WrongMoveException("Color not picked");

        if (game.lastDrawnCard() != null && !card.equals(game.lastDrawnCard()))
            throw new WrongMoveException("Only drawn card is playable");
    }

    protected void doPlayCard(Card card) {
        game.putInPlayDeck(card);
        card.applyAction(game);
    }

    abstract void play(Card card);
}
