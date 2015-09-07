package games.uno.utils;

import games.uno.domain.Deck;
import games.uno.RandomDeckFactory;

public class NonRandomDeckFactory extends RandomDeckFactory
{
    @Override
    public Deck generate() {
        initCards();
        return builder.build();
    }
}
