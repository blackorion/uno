package games.uno.domain.game;

import games.uno.domain.cards.AbstractCardHolder;
import games.uno.domain.cards.Card;
import java.util.List;

public class Player extends AbstractCardHolder
{
    private Long id;
    private String username;
    private boolean hasToMakeAMove = false;

    public Player(String username) { this.username = username; }

    public void setId(long id) { this.id = id; }

    public Long getId() { return id; }

    public String getUsername() { return username; }

    public List<Card> cardsOnHand() { return cards; }

    public boolean hasCardToPlay(Card onTop) {
        for ( Card testable : cards ) {
            if ( testable.isPlayable(onTop) )
                return true;
        }

        return false;
    }

    @Override
    public String toString() { return username; }

    public boolean hasToMakeAMove() {
        return hasToMakeAMove;
    }

    public void shouldMakeAMove() {
        hasToMakeAMove = true;
    }

    public void finishedHisMove() {
        hasToMakeAMove = false;
    }
}
