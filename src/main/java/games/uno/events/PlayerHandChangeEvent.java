package games.uno.events;

import games.cardgame.player.Player;
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
