package games.cardgame.cards;

import games.cardgame.core.GameMaster;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

abstract public class Card {
    protected final CardValue value;
    protected final CardColor color;

    public Card(CardValue value, CardColor color) {
        this.value = value;
        this.color = color;
    }

    public CardValue getValue() {
        return value;
    }

    public CardColor getColor() {
        return color;
    }

    public int getScore() {
        return value.getScore();
    }

    public void applyAction(GameMaster game) {
        value.applyAction(game);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return value + " " + color;
    }
}
