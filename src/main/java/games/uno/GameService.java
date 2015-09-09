package games.uno;

import games.uno.domain.cards.Card;
import games.uno.domain.game.Player;
import games.uno.domain.game.Uno;
import games.uno.util.DeckFactory;
import games.uno.web.messages.BoardInformationMessage;
import games.uno.web.messages.GameInfoMessage;
import games.uno.websockets.PlayerEventInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameService
{
    private final SimpMessagingTemplate messagingTemplate;
    private final PlayerEventInformer informer;
    private final Uno game;

    @Autowired
    public GameService(DeckFactory factory, SimpMessagingTemplate messagingTemplate, PlayerEventInformer informer) {
        this.messagingTemplate = messagingTemplate;
        this.informer = informer;
        this.game = new Uno(factory);
    }

    public void addPlayer(Player player) {
        game.addPlayer(player);
        messagingTemplate.convertAndSend("/topic/info", new BoardInformationMessage("Player " + player + " joined the game"));
        messagingTemplate.convertAndSend("/topic/game.players", game.players());
    }

    public void startNewGame() {
        game.start();
        messagingTemplate.convertAndSend("/topic/info", new BoardInformationMessage("Game is started!"));
        informer.sendHandToAllPlayers(currentCard());
    }

    public void stopCurrentGame() {
        game.finish();
        messagingTemplate.convertAndSend("/topic/info", new BoardInformationMessage("Game is stopped!"));
    }

    public Card currentCard() {
        return game.currentPlayedCard();
    }

    public void playCard(Player player, Card card) {
        if ( player != game.getCurrentPlayer() )
            return;

        game.playerPlaysA(card);
        informer.sendHandToAllPlayers(currentCard());
    }

    public GameInfoMessage getInfo() {
        return new GameInfoMessage(
                game.state(),
                game.playersSize(),
                game.getCurrentPlayer().getId(),
                game.currentPlayedCard(),
                game.bankRemains(),
                game.getDirection()
        );
    }
}