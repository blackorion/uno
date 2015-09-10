package games.uno.domain.cards;

public interface CardHolder {
    Card drawFromTop();

    Card draw(Card card);

    void take(Card card);

    default Card takeCardFrom(CardHolder cardHolder) {
        Card card = cardHolder.drawFromTop();
        take(card);

        return card;
    }

    default Card takeCardFrom(CardHolder cardHolder, Card card) {
        take(cardHolder.draw(card));

        return card;
    }

    default void takeAllFrom(CardHolder cardHolder) {
        while (cardHolder.remains() > 0)
            takeCardFrom(cardHolder);
    }

    int remains();
}
