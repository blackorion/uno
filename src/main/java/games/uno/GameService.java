package games.uno;

import games.uno.domain.Player;
import games.uno.domain.PlayersQueue;
import games.uno.domain.Uno;
import games.uno.util.DeckFactory;
import games.uno.web.messages.ApplicationError;
import games.uno.web.messages.BoardInformationMessage;
import games.uno.web.messages.GameInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private final SimpMessagingTemplate messagingTemplate;
    private final Uno game;

    @Autowired
    public GameService(DeckFactory factory, SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.game = new Uno(factory, new PlayersQueue());
    }

    public void addPlayer(Player player) {
        try {
            game.addPlayer(player);
            messagingTemplate.convertAndSend("/topic/info", new BoardInformationMessage("Player " + player + " joined the game"));
        } catch (Exception e) {
            messagingTemplate.convertAndSend("/queue/error", new ApplicationError(e));
        }
    }

    public boolean startNewGame() {
        try {
            game.start();
            messagingTemplate.convertAndSend("/topic/info", new BoardInformationMessage("Game is started!"));
            messagingTemplate.convertAndSend("/topic/events", getInfo());

            return true;
        } catch (Exception e) {
            messagingTemplate.convertAndSend("/queue/error", new ApplicationError(e));

            return false;
        }
    }

    public void stopCurrentGame() {
        game.finish();
        messagingTemplate.convertAndSend("/topic/info", new BoardInformationMessage("Game is stopped!"));
        messagingTemplate.convertAndSend("/topic/events", getInfo());
    }

    public GameInfo getInfo() {
        return new GameInfo(game.state().toString(), game.playersSize());
    }
}
