package games.uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck
{
    private List<Card> cards = new ArrayList<Card>();
    private int index = 0;

    public int size() {
        return cards.size();
    }

    public void add(Card card) {
        cards.add(card);
    }

    public Card pull() {
        return cards.get(index++);
    }

    public Card showTopCard() {
        return cards.get(cards.size() - 1);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
}
