package games.uno.events;

import games.uno.services.GameService;
import games.uno.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.util.Map;

@Component
public class WebsocketConnectionListener implements ApplicationListener<SessionConnectedEvent>
{
    private static final String SESSION_ATTR = "HTTPSESSIONID";

    @Autowired
    private PlayerService service;
    @Autowired
    private GameService gameService;
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        try {
            tryAddingUserToGame(event);
        }
        catch ( RuntimeException e ) {
            messagingTemplate.convertAndSendToUser(event.getUser().getName(), "/queue/errors", e.getMessage());
        }
    }

    private void tryAddingUserToGame(SessionConnectedEvent sessionConnectedEvent) {gameService.addPlayer(service.find(getSessionId(sessionConnectedEvent)));}

    private String getSessionId(SessionConnectedEvent sessionConnectedEvent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(sessionConnectedEvent.getMessage());
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap((GenericMessage) accessor.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER));
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();

        return (String) sessionAttributes.get(SESSION_ATTR);
    }
}
