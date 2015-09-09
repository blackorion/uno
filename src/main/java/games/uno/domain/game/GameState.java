package games.uno.domain.game;

import games.uno.exceptions.GameAlreadyStartedException;
import games.uno.exceptions.NoUsersInTheGameException;

public enum GameState
{
    RUNNING(true) {
        @Override
        public void start(Uno game) {
            throw new GameAlreadyStartedException();
        }

        @Override
        public void finish(Uno game) {
            game.setState(GameState.NOT_RUNNING);
            game.flush();
        }
    },

    NOT_RUNNING(false) {
        @Override
        public void start(Uno game) {
            if ( !game.hasPlayers() )
                throw new NoUsersInTheGameException();

            for ( Player player : game.players() )
                for ( int i = 0; i < 7; i++ )
                    player.takeCardFrom(game.getBankDeck());

            game.moveCardToPlayDeck();
            game.setState(RUNNING);
        }

        @Override
        public void finish(Uno game) {

        }
    };

    private final boolean isRunning;

    GameState(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public abstract void start(Uno game);

    public abstract void finish(Uno game);
}
