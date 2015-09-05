package games.uno;

public class WrongMoveException extends RuntimeException {
    public WrongMoveException(Card currentCard, Card playedCard) {
        super("Can't play " + playedCard + " on top of " + currentCard);
    }
}
