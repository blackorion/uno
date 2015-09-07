package games.uno.exceptions;

public class NoUsersInTheGameException extends RuntimeException
{
    private static final long serialVersionUID = 3L;

    public NoUsersInTheGameException() {
        super("Game has no users joined");
    }
}
