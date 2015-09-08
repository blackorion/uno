package games.uno;

import games.uno.domain.Card;
import games.uno.domain.Player;
import games.uno.domain.Uno;
import games.uno.util.TurnController;
import games.uno.web.messages.BoardInformationMessage;
import games.uno.web.messages.GameInfo;
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
        messagingTemplate.convertAndSend("/topic/game.players", game.players());
    }

    public void startNewGame() {
        game.start();
        messagingTemplate.convertAndSend("/topic/info", new BoardInformationMessage("Game is started!"));
    }

    public void stopCurrentGame() {
        game.finish();
        messagingTemplate.convertAndSend("/topic/info", new BoardInformationMessage("Game is stopped!"));
    }

    public GameInfo getInfo() {
        return new GameInfo(
                game.state(),
                game.playersSize(),
                game.getCurrentPlayer().getId(),
                game.currentPlayedCard(),
                game.bankRemains()
        );
    }

    public Card currentCard() {
        return game.currentPlayedCard();
    }
}