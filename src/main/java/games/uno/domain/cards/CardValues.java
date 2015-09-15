package games.uno.domain.cards;

public enum CardValues {
    DRAW_TWO(CardTypes.ACTION, 20),
    SKIP(CardTypes.ACTION, 20),
    REVERSE(CardTypes.ACTION, 20),
    WILD(CardTypes.ACTION, 50),
    WILD_DRAW_FOUR(CardTypes.ACTION, 50),

    ONE(CardTypes.NUMERIC, 1),
    TWO(CardTypes.NUMERIC, 2),
    THREE(CardTypes.NUMERIC, 3),
    FOUR(CardTypes.NUMERIC, 4),
    FIVE(CardTypes.NUMERIC, 5),
    SIX(CardTypes.NUMERIC, 6),
    SEVEN(CardTypes.NUMERIC, 7),
    EIGHT(CardTypes.NUMERIC, 8),
    NINE(CardTypes.NUMERIC, 9),
    ZERO(CardTypes.NUMERIC, 0);

    private final CardTypes type;
    private int score;

    CardValues(CardTypes type, int score) {
        this.type = type;
        this.score = score;
    }

    public CardTypes getType() {
        return type;
    }

    public int getScore() {
        return score;
    }
}
