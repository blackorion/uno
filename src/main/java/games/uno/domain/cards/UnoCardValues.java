package games.uno.domain.cards;

import games.cardgame.cards.CardType;
import games.cardgame.cards.CardValue;
import games.cardgame.core.GameMaster;
import games.uno.domain.cards.actions.*;

public enum UnoCardValues implements CardValue {
    DRAW_TWO(UnoCardTypes.ACTION, 20, new DrawTwoCardAction()),
    SKIP(UnoCardTypes.ACTION, 20, new SkipCardAction()),
    REVERSE(UnoCardTypes.ACTION, 20, new ReverseCardAction()),
    WILD(UnoCardTypes.ACTION, 50, new NoActionCard()),
    WILD_DRAW_FOUR(UnoCardTypes.ACTION, 50, new WildDrawFourCardAction()),

    ONE(UnoCardTypes.NUMERIC, 1, new NoActionCard()),
    TWO(UnoCardTypes.NUMERIC, 2, new NoActionCard()),
    THREE(UnoCardTypes.NUMERIC, 3, new NoActionCard()),
    FOUR(UnoCardTypes.NUMERIC, 4, new NoActionCard()),
    FIVE(UnoCardTypes.NUMERIC, 5, new NoActionCard()),
    SIX(UnoCardTypes.NUMERIC, 6, new NoActionCard()),
    SEVEN(UnoCardTypes.NUMERIC, 7, new NoActionCard()),
    EIGHT(UnoCardTypes.NUMERIC, 8, new NoActionCard()),
    NINE(UnoCardTypes.NUMERIC, 9, new NoActionCard()),
    ZERO(UnoCardTypes.NUMERIC, 0, new NoActionCard());

    private final CardType type;
    private int score;
    private CardAction action;

    UnoCardValues(CardType type, int score, CardAction action) {
        this.type = type;
        this.score = score;
        this.action = action;
    }

    @Override
    public CardType getType() {
        return type;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void applyAction(GameMaster game) {
        action.applyAction(game);
    }
}
