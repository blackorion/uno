package games.uno.domain.game;

import games.uno.exceptions.GameAlreadyStartedException;
import games.uno.exceptions.NoUsersInTheGameException;

public enum GameState
{
    RUNNING(true) {
        @Override
        void start(Uno game) {
            throw new GameAlreadyStartedException();
        }

        @Override
        void finish(Uno game) {
            game.setState(GameState.NOT_RUNNING);
            game.flush();
        }
    },

    NOT_RUNNING(false) {
        @Override
        void start(Uno game) {
            if ( !game.hasPlayers() )
                throw new NoUsersInTheGameException();

            game.getCurrentPlayer().shouldMakeAMove();
            game.setState(RUNNING);
        }

        @Override
        void finish(Uno game) {

        }
    };

    private final boolean isRunning;

    GameState(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean isRunning() {
        return isRunning;
    }

    abstract void start(Uno game);

    abstract void finish(Uno game);
}
