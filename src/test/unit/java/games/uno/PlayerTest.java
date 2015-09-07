package games.uno;

import games.uno.domain.*;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

public class PlayerTest {

    private final Player player = new Player("player");
    private final Card ONE_RED = new Card(CardValues.ONE, CardColors.RED);

    @Test
    public void shouldBePrintedAsUsername() {
        assertEquals("player", player.toString());
    }

    @Test
    public void canPullCardFromTopOfTheDeck() {
        Deck deck = new Deck();
        deck.add(ONE_RED);
        player.takeCardFrom(deck);

        assertThat(player.getCardsOnHand(), contains(ONE_RED));
    }

    @Test
    public void canGiveACard() {
        player.takeCard(ONE_RED);
        player.giveACard(ONE_RED);

        assertEquals(0, player.remains());
    }

    @Test
    public void shouldKnowIfHasCardOnHandToPlay() {
        assertFalse(player.hasCardToPlay(ONE_RED));

        player.takeCard(ONE_RED);
        assertTrue(player.hasCardToPlay(ONE_RED));
    }
}