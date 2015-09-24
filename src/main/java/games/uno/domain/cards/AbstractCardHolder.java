package games.uno.domain.cards;

import java.util.LinkedList;
import java.util.List;

public class AbstractCardHolder implements CardHolder {
    protected List<Card> cards = new LinkedList<>();

    @Override
    public Card drawFromTop() {
        return cards.remove(0);
    }

    @Override
    public Card draw(Card card) {
        if (card.isWild()) {
            for (Card examined : cards) {
                if (examined.getValue() == card.getValue()) {
                    cards.remove(examined);
                    break;
                }
            }
        } else
            cards.remove(card);

        return card;
    }

    @Override
    public void take(Card card) {
        cards.add(card);
    }

    @Override
    public int remains() {
        return cards.size();
    }

    public void dropAll() {
        cards = new LinkedList<>();
    }
}
