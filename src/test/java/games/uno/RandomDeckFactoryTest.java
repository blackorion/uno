package games.uno;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RandomDeckFactoryTest
{
    @Test
    public void has108Cards() {
        DeckFactory factory = new RandomDeckFactory();

        assertEquals(108, factory.generate().size());
    }
}