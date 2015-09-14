package games.uno.websockets;

import games.uno.PlayerRepository;
import games.uno.domain.cards.Card;
import games.uno.domain.game.Player;
import games.uno.web.PresentableCard;
import games.uno.web.PresentablePlayerHand;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerEventInformer {
    private SimpMessagingTemplate messagingTemplate;
    private PlayerRepository playerRepository;

    @Autowired
    public PlayerEventInformer(SimpMessagingTemplate messagingTemplate, PlayerRepository playerRepository) {
        this.messagingTemplate = messagingTemplate;
        this.playerRepository = playerRepository;
    }

    public void sendHandToAllPlayers(Card onTop, Pair<Player, Card> lastDrawnCard) {
        playerRepository.findAll().forEach(player -> {
            Card drawn = lastDrawnCard == null ? null : lastDrawnCard.getKey().equals(player) ? lastDrawnCard.getValue() : null;
            sendPlayerHand(player, drawn, onTop);
        });
    }

    public void sendPlayerHand(Player player, Card lastDrawn, Card onTop) {
        List<PresentableCard> cards = PresentableCard.fromCollection(player.cardsOnHand(), onTop);
        PresentablePlayerHand playerHand = new PresentablePlayerHand(cards, PresentableCard.fromCard(lastDrawn, onTop));
        messagingTemplate.convertAndSendToUser(player.getName(), "/topic/game.cards", playerHand);
    }

    public void sendPlayersListToAll(List<Player> players) {
        messagingTemplate.convertAndSend("/topic/game.players", players);
    }
}
