package games.uno.domain.cards.actions;

import games.cardgame.core.GameMaster;

public class SkipCardAction implements CardAction {
    @Override
    public void applyAction(GameMaster game) {
        if (!game.currentPlayer().cardsOnHand().isEmpty())
            game.nextPlayer();
    }
}
