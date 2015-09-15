package games.uno.domain.cards;

public class Card {
    private final CardValues value;
    private final CardColors color;

    public Card(CardValues value, CardColors color) {
        this.value = value;
        this.color = color;
    }

    public CardValues getValue() {
        return value;
    }

    public CardColors getColor() {
        return color;
    }

    public boolean isPlayable(Card topCard) {
        return topCard != null && (isWild() || isMatchesDiscard(topCard));
    }

    public boolean isWild() {
        return getValue() == CardValues.WILD || getValue() == CardValues.WILD_DRAW_FOUR;
    }

    private boolean isMatchesDiscard(Card topCard) {
        return topCard.getColor() == getColor() || topCard.getValue() == getValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card))
            return false;

        Card other = (Card) obj;

        if (isWild() && value == other.getValue() && (color == CardColors.DARK || other.getColor() == CardColors.DARK))
            return true;

        return (value == other.value && color == other.color);
    }

    @Override
    public int hashCode() {
        return 31 * value.hashCode() + 31 * color.hashCode();
    }

    @Override
    public String toString() {
        return value + " " + color;
    }

    public int getScore() {
        return value.getScore();
    }
}
