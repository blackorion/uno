package games.uno.domain.game;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.Deck;
import games.uno.testutils.NonRandomDeckFactory;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.IsNull;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UnoGameMasterTest {
    Deck deck = new Deck();
    Deck pill = new Deck();
    GameMaster master = new UnoGameMaster(deck, pill);

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

    @Test
    public void updateDeckFromPill_shouldFlushWildCardsColor() {
        pill.take(NonRandomDeckFactory.ONE_RED);
        pill.take(NonRandomDeckFactory.WILD_BLUE);

        master.updateDeckFromPill();

        assertThat(deck.remains(), CoreMatchers.is(1));
        assertThat(deck.showTopCard(), CoreMatchers.is(NonRandomDeckFactory.WILD_DARK));
    }

    @Test
    public void DidPlayerDrew_DrawCardCurrentTurn_ReturnTrue() {
        deck.take(NonRandomDeckFactory.ONE_BLUE);
        master.getTable().add(new Player("player"));

        master.drawCard();

        assertTrue(master.didPlayerDrewThisTurn());
    }

    @Test
    public void DidPlayerDrew_DidNotDrawCardCurrentTurn_ReturnFalse() {
        deck.take(NonRandomDeckFactory.ONE_BLUE);
        master.getTable().add(new Player("player"));

        assertFalse(master.didPlayerDrewThisTurn());
    }

    @Test
    public void DidPlayerDrew_DidDrawCardCurrentPreviousTurn_ReturnFalse() {
        deck.take(NonRandomDeckFactory.ONE_BLUE);
        master.getTable().add(new Player("player"));
        master.drawCard();

        master.nextPlayer();

        assertFalse(master.didPlayerDrewThisTurn());
    }

    @Test
    public void DidPlayerDrew_OnGameStart_ReturnFalse() {
        deck.take(NonRandomDeckFactory.ONE_BLUE);
        master.getTable().add(new Player("player"));
        master.drawCard();

        master.start();

        assertFalse(master.didPlayerDrewThisTurn());
    }

    @Test
    public void LastDrawnCard_CardWasDrawn_ReturnsACard() {
        deck.take(NonRandomDeckFactory.ONE_BLUE);
        master.getTable().add(new Player("player"));

        Card expected = master.drawCard();

        assertThat(master.lastDrawnCard(), is(expected));
    }

    @Test
    public void LastDrawnCard_CardWasNotDrawn_ReturnsNull() {
        deck.take(NonRandomDeckFactory.ONE_BLUE);
        master.getTable().add(new Player("player"));

        assertThat(master.lastDrawnCard(), IsNull.nullValue());
    }
}