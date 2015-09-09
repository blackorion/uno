package games.uno.domain.cards;

import java.util.ArrayList;
import java.util.List;

public class AbstractCardHolder implements CardHolder
{
    protected List<Card> cards = new ArrayList<>();
    protected int index = 0;

    @Override
    public Card drawFromTop() {
        return cards.get(index++);
    }

    @Override
    public Card draw(Card card) {
        cards.remove(card);

        return card;
    }

    @Override
    public void take(Card card) {
        cards.add(card);
    }

    @Override
    public int remains() { return cards.size() - index; }

    public void empty() {
        cards = new ArrayList<>();
        index = 0;
    }
}
