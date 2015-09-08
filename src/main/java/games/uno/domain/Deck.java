package games.uno.domain;

import java.util.Collections;

public class Deck extends AbstractCardHolder
{
    public Card showTopCard() {
        if ( remains() == 0 )
            return null;

        return cards.get(cards.size() - 1);
    }

    public void shuffle() { Collections.shuffle(cards); }

    public void refill() { index = 0; }
}
