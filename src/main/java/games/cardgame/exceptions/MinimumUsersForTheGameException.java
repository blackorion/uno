package games.cardgame.exceptions;

import static java.lang.String.format;

public class MinimumUsersForTheGameException extends RuntimeException {
    private static final long serialVersionUID = 3L;

    public MinimumUsersForTheGameException(int currentNumber) {
        super(format("Game should reach minimal number of joined users to start(%d of 2)", currentNumber));
    }
}
