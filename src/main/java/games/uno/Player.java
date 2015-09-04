package games.uno;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Player
{
    private Long id;
    private String username;
    private List<Card> cardsOnHand = new ArrayList<>();

    public Player(String username) { this.username = username; }

    public Long getId() { return id; }

    public String getUsername() { return username; }

    public Collection getCardsOnHand() { return cardsOnHand; }

    public void takeCard(Card card) { this.cardsOnHand.add(card); }

    @Override
    public String toString() { return username; }
}
