package games.cardgame.deck;

import games.cardgame.cardholder.AbstractCardHolder;
import games.uno.domain.cards.UnoCard;
import games.uno.domain.cards.UnoCardColors;

import java.util.Collections;
import java.util.stream.Collectors;

public class Deck extends AbstractCardHolder {
    public UnoCard showTopCard() {
        if (remains() == 0)
            return null;

        return cards.get(cards.size() - 1);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public void removeColoredWildCards() {
        cards = cards.parallelStream().map(card -> {
            if (card.isWild())
                return new UnoCard(card.getValue(), UnoCardColors.DARK);
            else
                return card;
        }).collect(Collectors.toList());
    }

    public UnoCard firstCardToDraw() {
        return cards.get(0);
    }
}
