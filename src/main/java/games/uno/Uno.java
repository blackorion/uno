package games.uno;

import games.uno.exceptions.NoUsersInTheGameException;
import java.util.ArrayList;
import java.util.List;

public class Uno
{
    private final Deck deck;
    private final Deck playDeck = new Deck();
    private List<User> users = new ArrayList<>();
    private boolean isStarted = false;

    public Uno(DeckFactory deckFactory) { deck = deckFactory.generate(); }

    public Deck getDeck() { return new Deck(); }

    public Card pullCard() { return deck.pull(); }

    public void addUser(User username) {
        if ( isStarted )
            throw new IllegalArgumentException("The game has already started.");

        users.add(username);
    }

    public void start() {
        if ( users.size() == 0 )
            throw new NoUsersInTheGameException();

        for ( User user : users )
            for ( int i = 0; i < 7; i++ )
                user.takeCard(deck.pull());

        playDeck.add(deck.pull());
        isStarted = true;
    }

    public Card currentPlayedCard() { return playDeck.showTopCard(); }
}
