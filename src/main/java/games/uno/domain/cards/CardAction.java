package games.uno.domain.cards;

import games.uno.domain.game.GameMaster;

public interface CardAction {
    void applyAction(GameMaster game);
}
