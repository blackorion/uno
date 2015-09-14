package games.uno.web;

import java.util.List;

public class PresentablePlayerHand {
    private List<PresentableCard> hand;
    private PresentableCard drawn;

    public PresentablePlayerHand() {
    }

    public PresentablePlayerHand(List<PresentableCard> hand, PresentableCard drawn) {
        this.hand = hand;
        this.drawn = drawn;

        removeDrawnFromHand();
        setPlayableFalseAllOtherThanDrawn();
    }

    private void setPlayableFalseAllOtherThanDrawn() {
        if (drawn == null)
            return;

        for (PresentableCard card : hand)
            card.setNotPlayable();
    }

    private void removeDrawnFromHand() {
        if (drawn == null)
            return;

        PresentableCard card = hand.get(hand.size() - 1);

        if (card.getValue() == drawn.getValue() && card.getColor() == drawn.getColor())
            hand.remove(card);
    }

    public List<PresentableCard> getHand() {
        return hand;
    }

    public PresentableCard getDrawn() {
        return drawn;
    }
}
