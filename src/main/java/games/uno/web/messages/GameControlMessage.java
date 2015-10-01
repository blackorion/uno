package games.uno.web.messages;

import games.uno.services.GameService;

public class GameControlMessage
{
    private GameAction action;

    public GameAction getAction() {
        return action;
    }

    public enum GameAction
    {
        START {
            @Override
            public void invoke(GameService service) {
                service.startNewGame();
            }
        },

        STOP {
            @Override
            public void invoke(GameService service) {
                service.stopCurrentGame();
            }
        };

        public abstract void invoke(GameService service);
    }
}
