package games.uno.domain.game;

import games.uno.domain.cards.AbstractCardHolder;
import games.uno.domain.cards.Card;
import java.util.List;

public class Player extends AbstractCardHolder
{
    private Long id;
    private String name;
    private boolean shouldPlay = false;

    public Player(String name) { this.name = name; }

    public void setId(long id) { this.id = id; }

    public Long getId() { return id; }

    public String getName() { return name; }

    public List<Card> cardsOnHand() { return cards; }

    public boolean hasCardToPlay(Card onTop) {
        for ( Card testable : cards ) {
            if ( testable.isPlayable(onTop) )
                return true;
        }

        return false;
    }

    @Override
    public String toString() { return name; }

    public boolean isShouldPlay() {
        return shouldPlay;
    }

    public int getCardsOnHand() {
        return remains();
    }

    public void shouldMakeAMove() {
        shouldPlay = true;
    }

    public void finishedHisMove() {
        shouldPlay = false;
    }

    public void setName(String name) {
        this.name = name;
    }
}
