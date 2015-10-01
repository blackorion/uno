package games.uno.domain.game.cardplaystrategies;

import games.cardgame.core.GameMaster;
import games.uno.domain.cards.UnoCard;
import games.uno.domain.game.UnoRulesManager;

public class FirstCardWildPlayStrategy extends CardPlayStrategy {
    public FirstCardWildPlayStrategy(UnoRulesManager rulesManager, GameMaster game) {
        super(rulesManager, game);
    }

    @Override
    public void play(UnoCard card) {
        verifyMove(card);
        doPlayCard(card);
        updateStrategy();
    }

    private void updateStrategy() {
        rulesManager.setDefaultCardPlayStrategy();
    }
}
