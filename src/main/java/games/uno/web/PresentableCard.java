package games.uno.web;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardColors;
import games.uno.domain.cards.CardValues;

public class PresentableCard
{
    private final CardValues value;
    private final CardColors color;
    private final boolean isPlayable;

    public PresentableCard(CardValues value, CardColors color, boolean isPlayable) {
        this.value = value;
        this.color = color;
        this.isPlayable = isPlayable;
    }

    public CardValues getValue() {
        return value;
    }

    public CardColors getColor() {
        return color;
    }

    public boolean isPlayable() {
        return isPlayable;
    }

    public static PresentableCard fromCard(Card card, Card topCard) {
        return new PresentableCard(card.getValue(), card.getColor(), card.isPlayable(topCard));
    }
}
