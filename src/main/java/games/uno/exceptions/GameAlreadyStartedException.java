package games.uno.exceptions;

public class GameAlreadyStartedException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public GameAlreadyStartedException() {
        super("Game has already started");
    }
}
