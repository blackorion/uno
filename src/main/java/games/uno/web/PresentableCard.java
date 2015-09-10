package games.uno.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardColors;
import games.uno.domain.cards.CardValues;
import java.util.List;
import java.util.stream.Collectors;

public class PresentableCard
{
    private CardValues value;
    private CardColors color;
    private boolean isPlayable;

    public PresentableCard() { }

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

    @JsonIgnore
    public Card getCard() {
        return new Card(value, color);
    }

    public static PresentableCard fromCard(Card card, Card topCard) {
        return new PresentableCard(card.getValue(), card.getColor(), card.isPlayable(topCard));
    }

    public static List<PresentableCard> fromCollection(List<Card> cards, Card topCard) {
        return cards.stream()
                .map(card -> PresentableCard.fromCard(card, topCard))
                .collect(Collectors.toList());
    }
}
