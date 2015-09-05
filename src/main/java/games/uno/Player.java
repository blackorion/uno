package games.uno;

import java.util.ArrayList;
import java.util.List;

public class Player implements CardHolder {
    private Long id;
    private String username;
    private List<Card> cardsOnHand = new ArrayList<>();

    public Player(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<Card> getCardsOnHand() {
        return cardsOnHand;
    }

    public void takeCard(Card card) {
        this.cardsOnHand.add(card);
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public Card giveACardFromTop() {
        return cardsOnHand.remove(cardsOnHand.size() - 1);
    }

    @Override
    public Card giveACard(Card card) {
        cardsOnHand.remove(card);

        return card;
    }

    @Override
    public int remains() {
        return cardsOnHand.size();
    }

    @Override
    public void takeCardFrom(CardHolder cardHolder) {
        cardsOnHand.add(cardHolder.giveACardFromTop());
    }

    public boolean hasCardToPlay(Card onTop) {
        for (Card testable : cardsOnHand) {
            if(testable.isPlayable(onTop))
                return true;
        }

        return false;
    }
}
