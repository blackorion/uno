package games.uno.events;

import games.uno.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class PlayerEventListener
{
    private final SimpMessagingTemplate messagingTemplate;
    private final PlayerRepository repository;

    @Autowired
    public PlayerEventListener(SimpMessagingTemplate messagingTemplate, PlayerRepository repository) {
        this.messagingTemplate = messagingTemplate;
        this.repository = repository;
    }

    @EventListener
    private void handleSessionConnected(SessionConnectedEvent event) {

    }
}
