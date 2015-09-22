package games.uno.domain.game;

import games.uno.domain.cards.AbstractCardHolder;
import games.uno.domain.cards.Card;
import games.uno.domain.cards.Deck;

public class UnoGameMaster implements GameMaster {
    private final Deck deck;
    private final Deck pill;
    private final GameTable table;
    private GameState state = GameState.NOT_RUNNING;
    private Card lastDrawnCard;

    public UnoGameMaster(Deck deck, Deck pill) {
        this.deck = deck;
        this.pill = pill;
        this.table = new GameTable(this);
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
    public Card currentPlayedCard() {
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
        deck.refill();
        deck.shuffle();
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
        Card card = pill.drawFromTop();
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
    public Card deckFirstCardToDraw() {
        return deck.firstCardToDraw();
    }

    @Override
    public boolean didPlayerDrewThisTurn() {
        return lastDrawnCard != null;
    }

    @Override
    public Card lastDrawnCard() {
        return lastDrawnCard;
    }

    @Override
    public void finishRound() {
        lastDrawnCard = null;
        stop();
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
    public Card drawCard() {
        if (deckIsEmpty())
            updateDeckFromPill();

        if (deckIsEmpty())
            return null;

        return lastDrawnCard = table.currentPlayer().takeCardFrom(deck);
    }

    @Override
    public void setPlayerFinishedMove() {
        table.currentPlayer().finishedHisMove();
    }

    @Override
    public void putInPlayDeck(Card card) {
        pill.takeCardFrom(currentPlayer(), card);
    }

    @Override
    public void nextPlayer() {
        table.nextTurn();
        lastDrawnCard = null;
    }
}
