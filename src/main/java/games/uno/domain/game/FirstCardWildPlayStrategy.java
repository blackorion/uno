package games.uno.domain.game;

import games.uno.domain.cards.Card;

public class FirstCardWildPlayStrategy extends CardPlayStrategy {
    public FirstCardWildPlayStrategy(UnoRulesManager rulesManager, GameMaster game) {
        super(rulesManager, game);
    }

    @Override
    public void play(Card card) {
        verifyMove(card);
        game.putInPlayDeck(card);
        rulesManager.handleCardAction(card);
        rulesManager.setDefaultCardPlayStrategy();
    }
}
