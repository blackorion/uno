package games.uno;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardColors;
import games.uno.domain.cards.CardValues;
import games.uno.domain.cards.Deck;
import games.uno.domain.game.Player;
import games.uno.testutils.NonRandomDeckFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class DeckTest {
    private Deck deck = new Deck();
    private final Card CARD_ONE_RED = new Card(CardValues.ONE, CardColors.RED);
    private final Card CARD_ONE_BLUE = new Card(CardValues.ONE, CardColors.BLUE);
    private final Card CARD_ONE_GREEN = new Card(CardValues.ONE, CardColors.GREEN);
    private final Card CARD_ONE_YELLOW = new Card(CardValues.ONE, CardColors.YELLOW);

    @Test
    public void shouldInsertCards() {
        deck.take(CARD_ONE_BLUE);
        deck.take(CARD_ONE_RED);

        assertEquals(2, deck.remains());
    }

    @Test
    public void PullCardFromTop_ShowsCardsRemains() {
        deck.take(CARD_ONE_BLUE);
        deck.take(CARD_ONE_RED);
        deck.take(CARD_ONE_GREEN);
        deck.take(CARD_ONE_YELLOW);
        assertThat(deck.remains(), is(4));

        deck.drawFromTop();

        assertThat(deck.remains(), is(3));
    }

    @Test
    public void cardCanBePulledFromTop() {
        deck.take(CARD_ONE_YELLOW);
        deck.take(CARD_ONE_GREEN);

        assertEquals(CARD_ONE_YELLOW, deck.drawFromTop());
        assertEquals(CARD_ONE_GREEN, deck.drawFromTop());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void throwsErrorWhenNoMoreCardsInDeck() {
        deck.take(CARD_ONE_GREEN);

        deck.drawFromTop();
        deck.drawFromTop();
    }

    @Test
    public void Draw_WildCard_WithSelectedColor() {
        deck.take(NonRandomDeckFactory.WILD_DARK);

        deck.draw(NonRandomDeckFactory.WILD_BLUE);

        assertThat(deck.remains(), is(0));
    }

    @Test
    public void DrawFrom_WildCard_WithSelectedColor() {
        Player player = new Player("player");
        player.take(NonRandomDeckFactory.WILD_BLUE);

        deck.takeCardFrom(player, NonRandomDeckFactory.WILD_BLUE);

        assertThat(deck.remains(), is(1));
    }
}