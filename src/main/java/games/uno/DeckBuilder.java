package games.uno;

import games.uno.domain.Card;
import games.uno.domain.CardColors;
import games.uno.domain.CardValues;
import games.uno.domain.Deck;

public class DeckBuilder
{
    private Deck deck = new Deck();

    public Deck build() {
        return deck;
    }

    public void add(CardValues value, CardColors color) {
        deck.take(new Card(value, color));
    }
}
