package games.uno.domain;

import games.uno.DeckFactory;
import games.uno.exceptions.*;
import games.uno.util.TurnController;
import java.util.ArrayList;
import java.util.List;

public class Uno
{
    private static final int MAX_PLAYERS = 15;
    private final Deck bankDeck;
    private final Deck playDeck = new Deck();
    private final TurnController turnController;
    private List<Player> players = new ArrayList<>();
    private boolean isStarted = false;
    private boolean currentPlayerFinishedHisTurn = false;

    public Uno(DeckFactory deckFactory, TurnController turnController) {
        this.turnController = turnController;
        bankDeck = deckFactory.generate();
    }

    public void addPlayer(Player player) {
        if ( players.contains(player) )
            throw new PlayerAlreadyInTheGameException(player);

        if ( isStarted )
            throw new IllegalArgumentException("The game has already started.");

        if ( players.size() == MAX_PLAYERS )
            throw new PlayerLimitForGameException();

        players.add(player);
    }

    public void start() {
        if ( isStarted )
            throw new GameAlreadyStartedException();

        if ( players.size() == 0 )
            throw new NoUsersInTheGameException();

        turnController.setPlayers(players);

        for ( Player player : players )
            for ( int i = 0; i < 7; i++ )
                player.take(bankDeck.drawFromTop());

        playDeck.take(bankDeck.drawFromTop());
        isStarted = true;
    }

    public Card currentPlayedCard() {
        return playDeck.showTopCard();
    }

    public Player getCurrentPlayer() {
        if ( !isStarted )
            return players.get(0);

        return turnController.currentPlayer();
    }

    public void endTurn() {
        if ( !currentPlayerFinishedHisTurn )
            throw new IllegalTurnEndException();

        turnController.nextTurn();
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
        isStarted = false;
        currentPlayerFinishedHisTurn = false;
        bankDeck.refill();
        bankDeck.shuffle();
        playDeck.empty();
        players.forEach(AbstractCardHolder::empty);
    }

    public String state() {
        return isStarted ? "RUNNING" : "NOT_RUNNING";
    }

    public int playersSize() {
        return players.size();
    }

    public int bankRemains() {
        return bankDeck.remains();
    }

    public List<Player> players() {
        return players;
    }
}
