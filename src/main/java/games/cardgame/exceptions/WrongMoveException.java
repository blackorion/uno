package games.cardgame.exceptions;

import games.uno.domain.cards.UnoCard;

public class WrongMoveException extends RuntimeException {
    private static final long serialVersionUID = 5L;

    public WrongMoveException(UnoCard currentCard, UnoCard playedCard) {
        super("Can't play " + playedCard + " on top of " + currentCard);
    }

    public WrongMoveException(String message) {
        super(message);
    }
}
