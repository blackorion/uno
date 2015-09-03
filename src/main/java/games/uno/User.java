package games.uno;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class User
{
    private String username;
    private List<Card> cards = new ArrayList<Card>();

    public User(String username) {

        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return username;
    }

    public Collection getCards() {
        return cards;
    }

    public void takeCard(Card card) {
        this.cards.add(card);
    }
}
