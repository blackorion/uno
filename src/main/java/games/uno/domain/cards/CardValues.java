package games.uno.domain.cards;

public enum CardValues
{
    DRAW_TWO(CardTypes.ACTION),
    SKIP(CardTypes.ACTION),
    REVERSE(CardTypes.ACTION),
    WILD(CardTypes.ACTION),
    WILD_DRAW_FOUR(CardTypes.ACTION),

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
