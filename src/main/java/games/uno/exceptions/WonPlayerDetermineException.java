package games.uno.exceptions;

public class WonPlayerDetermineException extends RuntimeException {
    private static final long serialVersionUID = 3596065862391683695L;

    public WonPlayerDetermineException() {
        super("Impossible to determine game winner(zero or more than one players with zero score)");
    }
}
