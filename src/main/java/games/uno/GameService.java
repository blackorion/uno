package games.uno;

import games.uno.web.messages.BoardInformationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameService
{
    private final SimpMessagingTemplate messagingTemplate;
    private final Uno game;

    @Autowired
    public GameService(DeckFactory factory, SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.game = new Uno(factory, new TurnController());
    }

    public void addPlayer(Player player) {
        game.addPlayer(player);
        messagingTemplate.convertAndSend("/topic/info", new BoardInformationMessage("Player " + player + " joined the game"));
    }

    public void startNewGame() {
        game.start();
        messagingTemplate.convertAndSend("/topic/info", new BoardInformationMessage("Game is started!"));
    }
}
