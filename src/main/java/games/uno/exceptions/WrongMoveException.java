package games.uno.exceptions;

import games.uno.domain.cards.Card;

public class WrongMoveException extends RuntimeException {
    private static final long serialVersionUID = 5L;

    public WrongMoveException(Card currentCard, Card playedCard) {
        super("Can't play " + playedCard + " on top of " + currentCard);
    }
}
