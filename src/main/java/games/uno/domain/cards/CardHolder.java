package games.uno.domain.cards;

public interface CardHolder
{
    Card drawFromTop();

    Card draw(Card card);

    void take(Card card);

    default void takeCardFrom(CardHolder cardHolder) {
        take(cardHolder.drawFromTop());
    }

    default void takeCardFrom(CardHolder cardHolder, Card card) {
        take(cardHolder.draw(card));
    }

    int remains();
}
