package games.uno.domain;

import java.util.List;

public class Player extends AbstractCardHolder
{
    private Long id;
    private String username;

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
}
