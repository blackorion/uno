package games.uno;

import games.uno.domain.Card;
import games.uno.domain.CardColors;
import games.uno.domain.CardValues;
import games.uno.domain.Deck;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;

public class DeckBuilderTest extends Assert
{
    @Test
    public void createsADeck() {
        DeckBuilder deckBuilder = new DeckBuilder();

        assertThat(deckBuilder.build(), is(instanceOf(Deck.class)));
    }

    @Test
    public void shouldInsertCardsOfRequiredTypeToDeck() {
        DeckBuilder deckBuilder = new DeckBuilder();
        deckBuilder.add(CardValues.TAKE_TWO, CardColors.RED);

        assertThat(deckBuilder.build().giveACardFromTop(), is(new Card(CardValues.TAKE_TWO, CardColors.RED)));
    }
}