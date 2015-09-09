package games.uno.domain;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardColors;
import games.uno.domain.cards.CardValues;
import games.uno.domain.cards.Deck;
import games.uno.domain.game.Player;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.hamcrest.Matchers.contains;

public class PlayerTest
{

    private final Player player = new Player("player");
    private final Card ONE_RED = new Card(CardValues.ONE, CardColors.RED);

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