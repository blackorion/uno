package games.uno.domain.cards;

import games.uno.domain.game.GameMaster;

public class ReverseCardAction implements CardAction {
    @Override
    public void applyAction(GameMaster game) {
        game.changeDirection();
    }
}
