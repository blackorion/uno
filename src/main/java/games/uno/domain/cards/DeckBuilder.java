package games.uno.domain.cards;

public class DeckBuilder {
    private Deck deck = new Deck();

    public Deck build() {
        return deck;
    }

    public DeckBuilder add(CardValues value, CardColors color) {
        deck.take(new Card(value, color));

        return this;
    }

    public DeckBuilder add(Card card) {
        deck.take(card);

        return this;
    }
}
