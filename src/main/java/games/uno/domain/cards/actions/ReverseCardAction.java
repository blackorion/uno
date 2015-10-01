package games.uno.domain.cards.actions;

import games.cardgame.core.GameMaster;

public class ReverseCardAction implements CardAction {
    @Override
    public void applyAction(GameMaster game) {
        game.changeDirection();
    }
}
