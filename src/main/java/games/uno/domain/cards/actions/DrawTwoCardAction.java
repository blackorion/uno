package games.uno.domain.cards.actions;

import games.cardgame.core.GameMaster;

public class DrawTwoCardAction implements CardAction {
    @Override
    public void applyAction(GameMaster game) {
        game.nextPlayer();
        game.drawCard(2);
    }
}
