package games.cardgame.exceptions;

public class IllegalTurnEndException extends RuntimeException
{
    private static final long serialVersionUID = 2L;

    public IllegalTurnEndException() {
        super("Illegal end of turn");
    }
}
