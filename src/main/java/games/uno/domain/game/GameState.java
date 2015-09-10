package games.uno.domain.game;

import games.uno.exceptions.GameAlreadyStartedException;

public enum GameState
{
    RUNNING(true) {
        @Override
        void start(GameMaster game) {
            throw new GameAlreadyStartedException();
        }

        @Override
        void finish(GameMaster game) {
            game.setState(GameState.NOT_RUNNING);
        }
    },

    NOT_RUNNING(false) {
        @Override
        void start(GameMaster game) {
            game.setPlayerShouldMove();
            game.setState(RUNNING);
        }

        @Override
        void finish(GameMaster game) {

        }
    };

    private final boolean isRunning;

    GameState(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean isRunning() {
        return isRunning;
    }

    abstract void start(GameMaster game);

    abstract void finish(GameMaster game);
}
