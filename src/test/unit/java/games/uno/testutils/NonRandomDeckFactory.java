package games.uno.testutils;

import games.uno.domain.cards.Deck;
import games.uno.util.RandomDeckFactory;

public class NonRandomDeckFactory extends RandomDeckFactory
{
    @Override
    public Deck generate() {
        initCards();
        return builder.build();
    }
}
