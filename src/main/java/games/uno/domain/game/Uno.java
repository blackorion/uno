package games.uno.domain.game;

import games.uno.domain.cards.AbstractCardHolder;
import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardHolder;
import games.uno.domain.cards.Deck;
import games.uno.exceptions.IllegalTurnEndException;
import games.uno.exceptions.WrongMoveException;
import games.uno.util.DeckFactory;
import java.util.List;

public class Uno
{
    private final Deck bankDeck;
    private final Deck playDeck = new Deck();
    private final GameTable table;

    private GameState state = GameState.NOT_RUNNING;
    private boolean currentPlayerFinishedHisTurn = false;

    public Uno(DeckFactory deckFactory) {
        this.table = new GameTable(this);
        bankDeck = deckFactory.generate();
    }

    public void addPlayer(Player player) {
        table.add(player);
    }

    public void start() {
        state.start(this);
    }

    public Card currentPlayedCard() {
        return playDeck.showTopCard();
    }

    public Player getCurrentPlayer() {
        return table.currentPlayer();
    }

    public void endTurn() {
        if ( !currentPlayerFinishedHisTurn )
            throw new IllegalTurnEndException();

        table.nextTurn();
        currentPlayerFinishedHisTurn = false;
    }

    public void playerPuts(Card card) {
        if ( !card.isPlayable(playDeck.showTopCard()) )
            throw new WrongMoveException(playDeck.showTopCard(), card);

        playDeck.takeCardFrom(getCurrentPlayer(), card);
        currentPlayerFinishedHisTurn = true;
        endTurn();
    }

    public void playerPullsFromDeck() {
        getCurrentPlayer().takeCardFrom(bankDeck);
        currentPlayerFinishedHisTurn = true;
    }

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

    public boolean hasPlayers() {
        return !table.isEmpty();
    }

    public CardHolder getBankDeck() {
        return bankDeck;
    }

    public void moveCardToPlayDeck() {
        playDeck.takeCardFrom(bankDeck);
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void flush() {
        currentPlayerFinishedHisTurn = false;
        bankDeck.refill();
        bankDeck.shuffle();
        playDeck.empty();
        table.players().forEach(AbstractCardHolder::empty);
    }
}
