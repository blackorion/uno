package games.cardgame.cardholder;

import games.uno.domain.cards.UnoCard;

public interface CardHolder {
    UnoCard drawFromTop();

    UnoCard draw(UnoCard card);

    void take(UnoCard card);

    default UnoCard takeCardFrom(CardHolder cardHolder) {
        UnoCard card = cardHolder.drawFromTop();
        take(card);

        return card;
    }

    default UnoCard takeCardFrom(CardHolder cardHolder, UnoCard card) {
        take(cardHolder.draw(card));

        return card;
    }

    default void takeAllFrom(CardHolder cardHolder) {
        while (cardHolder.remains() > 0)
            takeCardFrom(cardHolder);
    }

    int remains();
}
