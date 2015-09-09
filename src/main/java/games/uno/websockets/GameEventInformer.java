package games.uno.websockets;

import games.uno.web.messages.BoardInformationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class GameEventInformer
{
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public GameEventInformer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendInformationMessage(String message) {
        messagingTemplate.convertAndSend("/topic/game.info", new BoardInformationMessage(message));
    }
}
