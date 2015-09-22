package games.uno.domain.cards;

import games.uno.domain.game.GameMaster;

public class DrawTwoCardAction implements CardAction {
    @Override
    public void applyAction(GameMaster game) {
        game.nextPlayer();
        game.drawCard(2);
    }
}
