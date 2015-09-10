package games.uno.domain.game;

import games.uno.domain.cards.AbstractCardHolder;
import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardHolder;
import games.uno.domain.cards.Deck;

public class UnoGameMaster implements GameMaster
{
    private final Deck bankDeck;
    private final Deck playDeck;
    private final GameTable table;
    private GameState state = GameState.NOT_RUNNING;

    public UnoGameMaster(Deck bankDeck, Deck playDeck, GameTable gameTable) {
        this.bankDeck = bankDeck;
        this.playDeck = playDeck;
        this.table = gameTable;
    }

    @Override
    public Player currentPlayer() {
        return table.currentPlayer();
    }

    @Override
    public Card currentPlayedCard() {
        return playDeck.showTopCard();
    }

    @Override
    public void flipACard() {
        playDeck.takeCardFrom(bankDeck);
    }

    @Override
    public void playerDrawsFromDeck() {
        table.currentPlayer().takeCardFrom(bankDeck);
    }

    @Override
    public void changeDirection() {
        table.changeDirection();
    }

    @Override
    public void flush() {
        currentPlayer().finishedHisMove();
        bankDeck.refill();
        bankDeck.shuffle();
        playDeck.empty();
        table.players().forEach(AbstractCardHolder::empty);
    }

    @Override
    public void eachPlayer(EachPlayerAction action) {
        table.players().forEach(action::handle);
    }

    @Override
    public GameState state() {
        return state;
    }

    @Override
    public void setState(GameState state) {
        this.state = state;
    }

    @Override
    public void setPlayerShouldMove() {
        table.currentPlayer().shouldMakeAMove();
    }

    @Override
    public CardHolder bankDeck() {
        return bankDeck;
    }

    @Override
    public void drawCard() {
        table.currentPlayer().takeCardFrom(bankDeck);
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
    public void playA(Card card) {
        playDeck.takeCardFrom(currentPlayer(), card);
    }

    @Override
    public void endTurn() {
        table.nextTurn();
    }
}
