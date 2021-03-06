package games.uno.domain.game;

import games.cardgame.cardholder.AbstractCardHolder;
import games.cardgame.core.GameMaster;
import games.cardgame.core.GameTable;
import games.cardgame.deck.Deck;
import games.cardgame.deck.DeckFactory;
import games.cardgame.player.Player;
import games.uno.domain.cards.UnoCard;

import java.util.ArrayList;
import java.util.List;

public class UnoGameMaster implements GameMaster {
    private Deck deck;
    private final Deck pill;
    private final GameTable table;
    private final DeckFactory deckFactory;
    private GameState state = GameState.NOT_RUNNING;
    private UnoCard lastDrawnCard;

    public UnoGameMaster(DeckFactory deckFactory) {
        this.deckFactory = deckFactory;
        this.deck = makeDeck();
        this.pill = new Deck();
        this.table = new GameTable(this);
    }

    private Deck makeDeck() {
        return deckFactory.generate();
    }

    @Override
    public void start() {
        state.start(this);
        lastDrawnCard = null;
    }

    @Override
    public void stop() {
        state.finish(this);
        lastDrawnCard = null;
    }

    @Override
    public Player currentPlayer() {
        return table.currentPlayer();
    }

    @Override
    public UnoCard currentPlayedCard() {
        return pill.showTopCard();
    }

    @Override
    public void flipACard() {
        pill.takeCardFrom(deck);
    }

    @Override
    public void changeDirection() {
        table.changeDirection();
    }

    @Override
    public void flushDeckAndPill() {
        deck = makeDeck();
        pill.dropAll();
    }

    @Override
    public void flushPlayersHand() {
        currentPlayer().finishedHisMove();
        table.players().forEach(AbstractCardHolder::dropAll);
    }

    @Override
    public void giveEachPlayerCards(int number) {
        table.players().forEach(player -> {
            for (int i = 0; i < number; i++)
                player.takeCardFrom(deck);
        });
    }

    @Override
    public boolean isPlayerShouldPlay() {
        return currentPlayer().isShouldPlay();
    }

    @Override
    public GameState state() {
        return state;
    }

    @Override
    public void updateDeckFromPill() {
        UnoCard card = pill.drawFromTop();
        deck.dropAll();
        deck.takeAllFrom(pill);
        deck.removeColoredWildCards();
        pill.dropAll();
        pill.take(card);
    }

    @Override
    public int deckRemains() {
        return deck.remains();
    }

    @Override
    public UnoCard deckFirstCardToDraw() {
        return deck.firstCardToDraw();
    }

    @Override
    public boolean didPlayerDrewThisTurn() {
        return lastDrawnCard != null;
    }

    @Override
    public UnoCard lastDrawnCard() {
        return lastDrawnCard;
    }

    @Override
    public void finishRound() {
        lastDrawnCard = null;
        stop();
    }

    @Override
    public int cardsInPill() {
        return pill.remains();
    }

    @Override
    public boolean deckIsEmpty() {
        return deck.remains() == 0;
    }

    @Override
    public void shuffleDeck() {
        deck.shuffle();
    }

    @Override
    public GameTable getTable() {
        return table;
    }

    @Override
    public void setState(GameState state) {
        this.state = state;
    }

    @Override
    public void persuadePlayerToPlay() {
        table.currentPlayer().shouldMakeAMove();
    }

    @Override
    public UnoCard drawCard() {
        if (deckIsEmpty())
            updateDeckFromPill();

        if (deckIsEmpty())
            return null;

        return lastDrawnCard = table.currentPlayer().takeCardFrom(deck);
    }

    @Override
    public List<UnoCard> drawCard(int times) {
        List<UnoCard> cards = new ArrayList<>();

        for (int i = 0; i < times; i++)
            cards.add(drawCard());

        return cards;
    }

    @Override
    public void setPlayerFinishedMove() {
        table.currentPlayer().finishedHisMove();
    }

    @Override
    public void moveToPill(UnoCard card) {
        pill.takeCardFrom(currentPlayer(), card);
    }

    @Override
    public void nextPlayer() {
        table.nextTurn();
        lastDrawnCard = null;
    }
}
