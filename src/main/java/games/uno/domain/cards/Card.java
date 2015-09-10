package games.uno.domain.cards;

public class Card {
    private final CardValues value;
    private final CardColors color;

    public Card(CardValues value, CardColors color) {
        this.value = value;
        this.color = color;
    }

    public CardValues getValue() { return value; }

    public CardColors getColor() {
        return color;
    }

    public boolean isPlayable(Card topCard) {
        return topCard != null
                && (getColor() == CardColors.DARK
                || (topCard.getColor() == getColor() || topCard.getValue() == getValue()));

    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card))
            return false;

        Card other = (Card) obj;

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
}