package games.cardgame.exceptions;

public class PlayerLimitForGameException extends RuntimeException
{
    private static final long serialVersionUID = 4L;

    public PlayerLimitForGameException() {
        super("Game already reached max getElements");
    }
}
