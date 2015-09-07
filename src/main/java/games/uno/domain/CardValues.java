package games.uno.domain;

import games.uno.domain.CardTypes;

public enum CardValues
{
    TAKE_TWO(CardTypes.ACTION),
    TAKE_FOUR(CardTypes.ACTION),
    ONE_STEP_BACK(CardTypes.ACTION),
    PASS_TURN(CardTypes.ACTION),
    PICK_COLOR(CardTypes.ACTION),

    ONE(CardTypes.NUMERIC),
    TWO(CardTypes.NUMERIC),
    THREE(CardTypes.NUMERIC),
    FOUR(CardTypes.NUMERIC),
    FIVE(CardTypes.NUMERIC),
    SIX(CardTypes.NUMERIC),
    SEVEN(CardTypes.NUMERIC),
    EIGHT(CardTypes.NUMERIC),
    NINE(CardTypes.NUMERIC),
    ZERO(CardTypes.NUMERIC);

    private final CardTypes type;

    CardValues(CardTypes type) {
        this.type = type;
    }

    public CardTypes getType() {
        return type;
    }
}
