package games.uno.domain.game;

import games.uno.domain.cards.AbstractCardHolder;
import games.uno.domain.cards.Card;
import games.uno.domain.cards.Deck;

public class UnoGameMaster implements GameMaster {
    private final Deck deck;
    private final Deck pill;
    private final GameTable table;
    private GameState state = GameState.NOT_RUNNING;

    public UnoGameMaster(Deck deck, Deck pill, GameTable gameTable) {
        this.deck = deck;
        this.pill = pill;
        this.table = gameTable;
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
    public void flush() {
        currentPlayer().finishedHisMove();
        deck.refill();
        deck.shuffle();
        pill.empty();
        table.players().forEach(AbstractCardHolder::empty);
    }

    @Override
    public GameState state() {
        return state;
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
    public void updateDeckFromPill() {
        Card card = pill.drawFromTop();
        deck.takeAllFrom(pill);
        deck.flush();
        pill.empty();
        pill.take(card);
    }

    @Override
    public int deckRemains() {
        return deck.remains();
    }

    @Override
    public boolean deckIsEmpty() {
        return deck.remains() == 0;
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
        return table.currentPlayer().takeCardFrom(deck);
    }

    @Override
    public void setPlayerFinishedMove() {
        table.currentPlayer().finishedHisMove();
    }

    @Override
    public void start() {
        state.start(this);
    }

    @Override
    public void stop() {
        state.finish(this);
    }

    @Override
    public void putInPlayDeck(Card card) {
        pill.takeCardFrom(currentPlayer(), card);
    }

    @Override
    public void nextPlayer() {
        table.nextTurn();
    }
}
