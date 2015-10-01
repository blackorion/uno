package games.uno.domain.cards.actions;

import games.cardgame.core.GameMaster;

public interface CardAction {
    void applyAction(GameMaster game);
}
