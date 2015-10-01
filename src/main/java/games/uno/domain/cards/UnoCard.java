package games.uno.domain.cards;

import games.cardgame.cards.Card;
import games.cardgame.cards.CardColor;
import games.cardgame.cards.CardValue;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class UnoCard extends Card {
    public UnoCard(CardValue value, CardColor color) {
        super(value, color);
    }

    public boolean isPlayable(UnoCard topCard) {
        return topCard != null && (isWild() || isMatchesDiscard(topCard));
    }

    public boolean isWild() {
        return getValue() == UnoCardValues.WILD || getValue() == UnoCardValues.WILD_DRAW_FOUR;
    }

    private boolean isMatchesDiscard(UnoCard topCard) {
        return topCard.getColor() == getColor() || topCard.getValue() == getValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UnoCard))
            return false;

        UnoCard other = (UnoCard) obj;

        return isWildCardAndMatchesByValue(other) || super.equals(obj);
    }

    private boolean isWildCardAndMatchesByValue(UnoCard other) {
        return isWild() && getValue() == other.getValue() && (getColor() == UnoCardColors.DARK || other.getColor() == UnoCardColors.DARK);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
