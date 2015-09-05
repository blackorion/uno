package games.uno;

import games.uno.exceptions.NoUsersInTheGameException;

import java.util.ArrayList;
import java.util.List;

public class Uno {
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
        if (isStarted)
            throw new IllegalArgumentException("The game has already started.");

        players.add(player);
    }

    public void start() {
        if (players.size() == 0)
            throw new NoUsersInTheGameException();

        turnController.setPlayers(players);

        for (Player player : players)
            for (int i = 0; i < 7; i++)
                player.takeCard(bankDeck.giveACardFromTop());

        playDeck.add(bankDeck.giveACardFromTop());
        isStarted = true;
    }

    public Card currentPlayedCard() {
        return playDeck.showTopCard();
    }

    public Player getCurrentPlayer() {
        return turnController.currentPlayer();
    }

    public Player getNextPlayer() {
        return turnController.nextPlayer();
    }

    public void endTurn() {
        if (!currentPlayerFinishedHisTurn)
            throw new IllegalTurnEndException();

        turnController.nextTurn();
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
}
