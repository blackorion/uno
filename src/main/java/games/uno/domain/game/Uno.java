package games.uno.domain.game;

import games.uno.domain.cards.AbstractCardHolder;
import games.uno.domain.cards.Card;
import games.uno.domain.cards.Deck;
import games.uno.exceptions.GameAlreadyStartedException;
import games.uno.exceptions.IllegalTurnEndException;
import games.uno.exceptions.NoUsersInTheGameException;
import games.uno.exceptions.WrongMoveException;
import games.uno.util.DeckFactory;

import java.util.List;

public class Uno {
    private final Deck bankDeck;
    private final Deck playDeck = new Deck();
    private final PlayersQueue playersQueue;
    private final GameTable table;
    private GameState state = GameState.STOPPED;
    private boolean currentPlayerFinishedHisTurn = false;

    public Uno(DeckFactory deckFactory, PlayersQueue playersQueue) {
        this.playersQueue = playersQueue;
        this.table = new GameTable(this);
        bankDeck = deckFactory.generate();
    }

    public void addPlayer(Player player) {
        table.add(player);
    }

    public void start() {
        if (state == GameState.RUNNING)
            throw new GameAlreadyStartedException();

        if (table.isEmpty())
            throw new NoUsersInTheGameException();

        playersQueue.setPlayers(table.players());

        for (Player player : table.players())
            for (int i = 0; i < 7; i++)
                player.take(bankDeck.drawFromTop());

        playDeck.take(bankDeck.drawFromTop());
        state = GameState.RUNNING;
    }

    public Card currentPlayedCard() {
        return playDeck.showTopCard();
    }

    public Player getCurrentPlayer() {
        return playersQueue.currentPlayer();
    }

    public void endTurn() {
        if (!currentPlayerFinishedHisTurn)
            throw new IllegalTurnEndException();

        playersQueue.nextTurn();
        currentPlayerFinishedHisTurn = false;
    }

    public void playerPuts(Card card) {
        if (!card.isPlayable(playDeck.showTopCard()))
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
        state = GameState.STOPPED;
        currentPlayerFinishedHisTurn = false;
        bankDeck.refill();
        bankDeck.shuffle();
        playDeck.empty();
        table.players().forEach(AbstractCardHolder::empty);
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

    public enum GameState {
        RUNNING(true), STOPPED(false);

        private final boolean isRunning;

        GameState(boolean isRunning) {
            this.isRunning = isRunning;
        }

        public boolean isRunning() {
            return isRunning;
        }
    }
}
