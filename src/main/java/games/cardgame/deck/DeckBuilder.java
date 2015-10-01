package games.cardgame.deck;

import games.uno.domain.cards.UnoCard;
import games.uno.domain.cards.UnoCardColors;
import games.uno.domain.cards.UnoCardValues;

public class DeckBuilder {
    private Deck deck = new Deck();

    public Deck build() {
        return deck;
    }

    public DeckBuilder add(UnoCardValues value, UnoCardColors color) {
        deck.take(new UnoCard(value, color));

        return this;
    }

    public DeckBuilder add(UnoCard card) {
        deck.take(card);

        return this;
    }
}
