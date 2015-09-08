package games.uno.web;

import games.uno.domain.CardColors;
import games.uno.domain.CardValues;

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
}
