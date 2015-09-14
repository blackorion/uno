package games.uno.domain.cards;

import java.util.Collections;
import java.util.stream.Collectors;

public class Deck extends AbstractCardHolder {
    public Card showTopCard() {
        if (remains() == 0)
            return null;

        return cards.get(cards.size() - 1);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public void refill() {
        index = 0;
    }

    public void removeColoredWildCards() {
        cards = cards.parallelStream().map(card -> {
            if (card.isWild())
                return new Card(card.getValue(), CardColors.DARK);
            else
                return card;
        }).collect(Collectors.toList());
    }

    public Card firstCardToDraw() {
        return cards.get(index);
    }
}
