package games.cardgame.deck;

import games.uno.domain.cards.UnoCard;
import games.uno.domain.cards.UnoCardColors;
import games.uno.domain.cards.UnoCardValues;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;

public class DeckBuilderTest extends Assert
{
    public static final UnoCard DRAW_TWO_RED_CARD = new UnoCard(UnoCardValues.DRAW_TWO, UnoCardColors.RED);

    @Test
    public void createsADeck() {
        DeckBuilder deckBuilder = new DeckBuilder();

        assertThat(deckBuilder.build(), is(instanceOf(Deck.class)));
    }

    @Test
    public void shouldInsertCardsOfRequiredTypeToDeck() {
        DeckBuilder deckBuilder = new DeckBuilder();
        deckBuilder.add(UnoCardValues.DRAW_TWO, UnoCardColors.RED);

        assertThat(deckBuilder.build().drawFromTop(), is(DRAW_TWO_RED_CARD));
    }
}