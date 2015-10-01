package games.cardgame.player;

import games.cardgame.deck.Deck;
import games.uno.domain.cards.UnoCard;
import games.uno.domain.cards.UnoCardColors;
import games.uno.domain.cards.UnoCardValues;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

public class PlayerTest
{

    private final Player player = new Player("player");
    private final UnoCard ONE_RED = new UnoCard(UnoCardValues.ONE, UnoCardColors.RED);

    @Test
    public void shouldBePrintedAsUsername() {
        assertEquals("player", player.toString());
    }

    @Test
    public void canPullCardFromTopOfTheDeck() {
        Deck deck = new Deck();
        deck.take(ONE_RED);
        player.takeCardFrom(deck);

        assertThat(player.cardsOnHand(), contains(ONE_RED));
    }

    @Test
    public void canGiveACard() {
        player.take(ONE_RED);
        player.draw(ONE_RED);

        assertEquals(0, player.remains());
    }

    @Test
    public void shouldKnowIfHasCardOnHandToPlay() {
        assertFalse(player.hasCardToPlay(ONE_RED));

        player.take(ONE_RED);
        assertTrue(player.hasCardToPlay(ONE_RED));
    }
}