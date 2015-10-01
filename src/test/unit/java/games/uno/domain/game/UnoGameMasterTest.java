package games.uno.domain.game;

import games.cardgame.core.GameMaster;
import games.cardgame.deck.Deck;
import games.cardgame.deck.DeckFactory;
import games.cardgame.player.Player;
import games.cardgame.testutils.NonRandomDeckFactory;
import games.uno.domain.cards.UnoCard;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UnoGameMasterTest {
    Deck deck = new Deck();
    GameMaster master;

    @Before
    public void setUp() throws Exception {
        DeckFactory stubFactory = Mockito.mock(DeckFactory.class);
        when(stubFactory.generate()).thenReturn(deck);
        master = new UnoGameMaster(stubFactory);
        master.getTable().add(new Player("player"));
    }

    @Test
    public void BankIsEmpty() {
        assertTrue(master.deckIsEmpty());
    }

    @Test
    public void updateDeckFromPill() {
        deck.take(NonRandomDeckFactory.DRAW_TWO_RED);
        deck.drawFromTop();
        master.moveToPill(NonRandomDeckFactory.ONE_RED);
        master.moveToPill(NonRandomDeckFactory.TWO_BLUE);

        master.updateDeckFromPill();

        assertThat(master.cardsInPill(), is(1));
        assertThat(master.currentPlayedCard(), is(NonRandomDeckFactory.ONE_RED));
        assertThat(deck.remains(), is(1));
        assertThat(deck.showTopCard(), is(NonRandomDeckFactory.TWO_BLUE));
    }

    @Test
    public void updateDeckFromPill_shouldFlushWildCardsColor() {
        master.moveToPill(NonRandomDeckFactory.ONE_RED);
        master.moveToPill(NonRandomDeckFactory.WILD_BLUE);

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

        UnoCard expected = master.drawCard();

        assertThat(master.lastDrawnCard(), is(expected));
    }

    @Test
    public void LastDrawnCard_CardWasNotDrawn_ReturnsNull() {
        deck.take(NonRandomDeckFactory.ONE_BLUE);
        master.getTable().add(new Player("player"));

        assertThat(master.lastDrawnCard(), IsNull.nullValue());
    }

    @Test
    public void DrawCard_DeckIsEmpty_PillShuffledToNewDeck() {
        master.moveToPill(NonRandomDeckFactory.ONE_RED);
        master.moveToPill(NonRandomDeckFactory.TWO_BLUE);
        master.moveToPill(NonRandomDeckFactory.THREE_BLUE);

        UnoCard drawn = master.drawCard();

        assertThat(drawn, is(NonRandomDeckFactory.TWO_BLUE));
        assertThat(deck.showTopCard(), is(NonRandomDeckFactory.THREE_BLUE));
        assertThat(master.currentPlayedCard(), is(NonRandomDeckFactory.ONE_RED));
    }

    @Test
    public void DrawCard_EmptyDeckAndPill_NoCardDrawn() {
        master.moveToPill(NonRandomDeckFactory.ONE_BLUE);

        UnoCard drawn = master.drawCard();

        assertThat(drawn, is(nullValue()));
    }
}