package games.uno.domain.cards;

import games.uno.domain.game.GameMaster;

public enum CardValues {
    DRAW_TWO(CardTypes.ACTION, 20, new DrawTwoCardAction()),
    SKIP(CardTypes.ACTION, 20, new SkipCardAction()),
    REVERSE(CardTypes.ACTION, 20, new ReverseCardAction()),
    WILD(CardTypes.ACTION, 50, new NoActionCard()),
    WILD_DRAW_FOUR(CardTypes.ACTION, 50, new WildDrawFourCardAction()),

    ONE(CardTypes.NUMERIC, 1, new NoActionCard()),
    TWO(CardTypes.NUMERIC, 2, new NoActionCard()),
    THREE(CardTypes.NUMERIC, 3, new NoActionCard()),
    FOUR(CardTypes.NUMERIC, 4, new NoActionCard()),
    FIVE(CardTypes.NUMERIC, 5, new NoActionCard()),
    SIX(CardTypes.NUMERIC, 6, new NoActionCard()),
    SEVEN(CardTypes.NUMERIC, 7, new NoActionCard()),
    EIGHT(CardTypes.NUMERIC, 8, new NoActionCard()),
    NINE(CardTypes.NUMERIC, 9, new NoActionCard()),
    ZERO(CardTypes.NUMERIC, 0, new NoActionCard());

    private final CardTypes type;
    private int score;
    private CardAction action;

    CardValues(CardTypes type, int score, CardAction action) {
        this.type = type;
        this.score = score;
        this.action = action;
    }

    public CardTypes getType() {
        return type;
    }

    public int getScore() {
        return score;
    }

    public void applyAction(GameMaster game) {
        action.applyAction(game);
    }
}
