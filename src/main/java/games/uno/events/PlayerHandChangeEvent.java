package games.uno.events;

import games.uno.domain.game.Player;
import org.springframework.context.ApplicationEvent;

public class PlayerHandChangeEvent extends ApplicationEvent {
    private Player player;

    public PlayerHandChangeEvent(Object source) {
        super(source);
    }

    public Player getPlayer() {
        return player;
    }
}
