package games.uno.domain.cards.actions;

import games.cardgame.core.GameMaster;

public class WildDrawFourCardAction implements CardAction {
    @Override
    public void applyAction(GameMaster game) {
        game.nextPlayer();
        game.drawCard(4);
    }
}
