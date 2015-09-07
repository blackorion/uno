package games.uno.events;

import games.uno.GameService;
import games.uno.PlayerRepository;
import games.uno.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.util.Map;

@Component
public class WebsocketConnectionListener implements ApplicationListener<SessionConnectedEvent> {
    private static final String SESSION_ATTR = "HTTPSESSIONID";

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameService gameService;

    @Override
    public void onApplicationEvent(SessionConnectedEvent sessionConnectedEvent) {
        String sessionId = getSessionId(sessionConnectedEvent);
        Player player = playerRepository.findBySessionId(sessionId);
        gameService.addPlayer(player);
    }

    private String getSessionId(SessionConnectedEvent sessionConnectedEvent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(sessionConnectedEvent.getMessage());
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap((GenericMessage) accessor.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER));
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();

        return (String) sessionAttributes.get(SESSION_ATTR);
    }
}
