package games.uno.domain.cards;

import games.uno.domain.game.GameMaster;

public class SkipCardAction implements CardAction {
    @Override
    public void applyAction(GameMaster game) {
        game.nextPlayer();
    }
}
