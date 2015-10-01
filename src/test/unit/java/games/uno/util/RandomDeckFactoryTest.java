package games.uno.util;

import games.cardgame.deck.DeckFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RandomDeckFactoryTest
{
    @Test
    public void has108Cards() {
        DeckFactory factory = new RandomDeckFactory();

        assertEquals(108, factory.generate().remains());
    }
}