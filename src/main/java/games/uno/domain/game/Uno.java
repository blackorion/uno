package games.uno.domain.game;

import games.uno.domain.cards.AbstractCardHolder;
import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardHolder;
import games.uno.domain.cards.Deck;
import games.uno.exceptions.IllegalTurnEndException;
import games.uno.exceptions.WrongMoveException;
import games.uno.util.DeckFactory;
import java.util.List;

public class Uno implements Game
{
    private final Deck bankDeck;
    private final Deck playDeck = new Deck();
    private final RulesManager rulesManager;
    private final GameTable table;

    private GameState state = GameState.NOT_RUNNING;

    public Uno(DeckFactory deckFactory) {
        this.table = new GameTable(this);
        bankDeck = deckFactory.generate();
        rulesManager = new UnoRulesManager(this);
    }

    @Override
    public void addPlayer(Player player) {
        table.add(player);
    }

    @Override
    public void start() { state.start(this); }

    public Card currentPlayedCard() {
        return playDeck.showTopCard();
    }

    @Override
    public Player getCurrentPlayer() {
        return table.currentPlayer();
    }

    @Override
    public void endTurn() {
        if ( getCurrentPlayer().hasToMakeAMove() )
            throw new IllegalTurnEndException();

        table.nextTurn();
        getCurrentPlayer().shouldMakeAMove();
    }

    public void playerDrawsFromDeck() {
        getCurrentPlayer().takeCardFrom(bankDeck);
        getCurrentPlayer().finishedHisMove();
    }

    @Override
    public void finish() {
        state.finish(this);
    }

    public int playersSize() {
        return table.players().size();
    }

    public int bankRemains() {
        return bankDeck.remains();
    }

    public List<Player> players() {
        return table.players();
    }

    public GameState state() {
        return state;
    }

    public BidirectionalQueue.Direction getDirection() {
        return table.getDirection();
    }

    @Override
    public boolean hasPlayers() {
        return !table.isEmpty();
    }

    public CardHolder getBankDeck() {
        return bankDeck;
    }

    public void flipACard() {
        playDeck.takeCardFrom(bankDeck);
    }

    public void setState(GameState state) {
        this.state = state;
    }

    @Override
    public void flush() {
        getCurrentPlayer().finishedHisMove();
        bankDeck.refill();
        bankDeck.shuffle();
        playDeck.empty();
        table.players().forEach(AbstractCardHolder::empty);
    }

    @Override
    public void playerPlaysA(Card card) {
        if ( !card.isPlayable(playDeck.showTopCard()) )
            throw new WrongMoveException(playDeck.showTopCard(), card);

        playDeck.takeCardFrom(getCurrentPlayer(), card);
        getCurrentPlayer().finishedHisMove();
        endTurn();
    }
}
