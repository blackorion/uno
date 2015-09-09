package games.uno.events;

import games.uno.GameService;
import games.uno.PlayerRepository;
import games.uno.websockets.PlayerEventInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class PlayerEventListener {
    private final PlayerEventInformer informer;
    private final PlayerRepository repository;
    private GameService service;

    @Autowired
    public PlayerEventListener(PlayerEventInformer informer, PlayerRepository repository) {
        this.informer = informer;
        this.repository = repository;
    }

    @Autowired
    public void setService(GameService service) {
        this.service = service;
    }

    @EventListener
    private void handleSessionConnected(SessionConnectedEvent event) {

    }

    @EventListener
    private void handlePlaterHandChange(PlayerHandChangeEvent event) {
        informer.sendPlayerHand(event.getPlayer(), service.currentCard());
    }
}
