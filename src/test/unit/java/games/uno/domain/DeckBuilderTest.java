package games.uno.domain;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;

public class DeckBuilderTest extends Assert
{
    public static final Card DRAW_TWO_RED_CARD = new Card(CardValues.DRAW_TWO, CardColors.RED);

    @Test
    public void createsADeck() {
        DeckBuilder deckBuilder = new DeckBuilder();

        assertThat(deckBuilder.build(), is(instanceOf(Deck.class)));
    }

    @Test
    public void shouldInsertCardsOfRequiredTypeToDeck() {
        DeckBuilder deckBuilder = new DeckBuilder();
        deckBuilder.add(CardValues.DRAW_TWO, CardColors.RED);

        assertThat(deckBuilder.build().drawFromTop(), is(DRAW_TWO_RED_CARD));
    }
}