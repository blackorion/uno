package games.cardgame.cards;

import games.cardgame.core.GameMaster;

public interface CardValue {
    CardType getType();

    int getScore();

    void applyAction(GameMaster game);
}
