package games.uno.domain.game.cardplaystrategies;

import games.cardgame.core.GameMaster;
import games.cardgame.exceptions.WrongMoveException;
import games.uno.domain.cards.UnoCard;
import games.uno.domain.cards.UnoCardColors;
import games.uno.domain.game.UnoRulesManager;

abstract public class CardPlayStrategy {
    protected final UnoRulesManager rulesManager;
    protected final GameMaster game;

    public CardPlayStrategy(UnoRulesManager rulesManager, GameMaster game) {
        this.rulesManager = rulesManager;
        this.game = game;
    }

    protected void verifyMove(UnoCard card) {
        if (!card.isPlayable(game.currentPlayedCard()))
            throw new WrongMoveException(game.currentPlayedCard(), card);

        if (card.isWild() && card.getColor() == UnoCardColors.DARK)
            throw new WrongMoveException("Color not picked");

        if (game.lastDrawnCard() != null && !card.equals(game.lastDrawnCard()))
            throw new WrongMoveException("Only drawn card is playable");
    }

    protected void doPlayCard(UnoCard card) {
        game.moveToPill(card);
        card.applyAction(game);
    }

    public abstract void play(UnoCard card);
}
