package games.uno.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import games.cardgame.cards.CardColor;
import games.cardgame.cards.CardValue;
import games.uno.domain.cards.UnoCard;

import java.util.List;
import java.util.stream.Collectors;

public class PresentableCard {
    private CardValue value;
    private CardColor color;
    private boolean isPlayable;

    public PresentableCard() {
    }

    public PresentableCard(CardValue value, CardColor color, boolean isPlayable) {
        this.value = value;
        this.color = color;
        this.isPlayable = isPlayable;
    }

    public CardValue getValue() {
        return value;
    }

    public CardColor getColor() {
        return color;
    }

    public boolean isPlayable() {
        return isPlayable;
    }

    @JsonIgnore
    public UnoCard getCard() {
        return new UnoCard(value, color);
    }

    public static PresentableCard fromCard(UnoCard card, UnoCard topCard) {
        if (card == null)
            return null;

        return new PresentableCard(card.getValue(), card.getColor(), card.isPlayable(topCard));
    }

    public static List<PresentableCard> fromCollection(List<UnoCard> cards, UnoCard topCard) {
        return cards.stream()
                .map(card -> PresentableCard.fromCard(card, topCard))
                .collect(Collectors.toList());
    }

    public void setNotPlayable() {
        isPlayable = false;
    }
}
