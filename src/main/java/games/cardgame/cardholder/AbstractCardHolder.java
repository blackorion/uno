package games.cardgame.cardholder;

import games.uno.domain.cards.UnoCard;

import java.util.LinkedList;
import java.util.List;

public class AbstractCardHolder implements CardHolder {
    protected List<UnoCard> cards = new LinkedList<>();

    @Override
    public UnoCard drawFromTop() {
        return cards.remove(0);
    }

    @Override
    public UnoCard draw(UnoCard card) {
        if (card.isWild()) {
            for (UnoCard examined : cards) {
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
    public void take(UnoCard card) {
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
