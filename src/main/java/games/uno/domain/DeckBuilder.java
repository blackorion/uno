package games.uno.domain;

public class DeckBuilder {
    private Deck deck = new Deck();

    public Deck build() {
        return deck;
    }

    public void add(CardValues value, CardColors color) {
        deck.take(new Card(value, color));
    }
}
