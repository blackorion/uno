package games.uno.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck implements CardHolder
{
    private List<Card> cards = new ArrayList<>();
    private int index = 0;

    @Override
    public int remains() { return cards.size() - index; }

    @Override
    public void takeCardFrom(CardHolder cardHolder) {
        cards.add(cardHolder.giveACardFromTop());
    }

    public void add(Card card) { cards.add(card); }

    public Card giveACardFromTop() { return cards.get(index++); }

    @Override
    public Card giveACard(Card card) {
        cards.remove(card);

        return card;
    }

    public Card showTopCard() {
        if ( cards.size() == 0 )
            return null;

        return cards.get(cards.size() - 1);
    }

    public void shuffle() { Collections.shuffle(cards); }

    public void takeCardFrom(CardHolder cardHolder, Card card) { cards.add(cardHolder.giveACard(card)); }

    public void refill() { index = 0; }

    public void empty() {
        cards = new ArrayList<>();
        index = 0;
    }
}
