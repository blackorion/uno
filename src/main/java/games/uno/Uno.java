package games.uno;

import games.uno.exceptions.NoUsersInTheGameException;
import java.util.ArrayList;
import java.util.List;

public class Uno
{
    private final Deck deck;
    private final Deck playDeck = new Deck();
    private List<Player> players = new ArrayList<>();
    private boolean isStarted = false;
    private int currentPlayerIndex = 0;

    public Uno(DeckFactory deckFactory) { deck = deckFactory.generate(); }

    public Deck getDeck() { return new Deck(); }

    public Card pullCard() { return deck.pull(); }

    public void addPlayer(Player username) {
        if ( isStarted )
            throw new IllegalArgumentException("The game has already started.");

        players.add(username);
    }

    public void start() {
        if ( players.size() == 0 )
            throw new NoUsersInTheGameException();

        for ( Player player : players )
            for ( int i = 0; i < 7; i++ )
                player.takeCard(deck.pull());

        playDeck.add(deck.pull());
        isStarted = true;
    }

    public Card currentPlayedCard() { return playDeck.showTopCard(); }

    public Player getCurrentPlayer() {
        return getPlayerByIndex(currentPlayerIndex);
    }

    public Player getNextPlayer() {
        return getPlayerByIndex(nextTurnIndex());
    }

    private Player getPlayerByIndex(int currentPlayerIndex) {return players.get(currentPlayerIndex);}

    public void endTurn() {
        currentPlayerIndex = nextTurnIndex();
    }

    private int nextTurnIndex() {
        int result = currentPlayerIndex + 1;

        if ( result == players.size() )
            return 0;
        else
            return result;
    }
}
