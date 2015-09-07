package games.uno.domain;

public interface CardHolder {
    Card giveACardFromTop();

    Card giveACard(Card card);

    void takeCardFrom(CardHolder cardHolder);

    int remains();
}
