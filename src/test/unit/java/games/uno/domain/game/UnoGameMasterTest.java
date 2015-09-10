package games.uno.domain.game;

import games.uno.domain.cards.Deck;
import games.uno.testutils.NonRandomDeckFactory;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UnoGameMasterTest {
    Deck deck = new Deck();
    Deck pill = new Deck();
    GameTable table = Mockito.mock(GameTable.class);
    GameMaster master = new UnoGameMaster(deck, pill, table);

    @Test
    public void BankIsEmpty() {
        assertTrue(master.deckIsEmpty());
    }

    @Test
    public void updateDeckFromPill() {
        deck.take(NonRandomDeckFactory.DRAW_TWO_RED);
        deck.drawFromTop();
        pill.take(NonRandomDeckFactory.ONE_RED);
        pill.take(NonRandomDeckFactory.TWO_BLUE);

        master.updateDeckFromPill();

        assertThat(pill.remains(), is(1));
        assertThat(master.currentPlayedCard(), is(NonRandomDeckFactory.ONE_RED));
        assertThat(deck.remains(), is(1));
        assertThat(deck.showTopCard(), is(NonRandomDeckFactory.TWO_BLUE));
    }
}