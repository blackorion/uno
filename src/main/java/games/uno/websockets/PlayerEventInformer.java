package games.uno.websockets;

import games.cardgame.player.Player;
import games.uno.domain.cards.UnoCard;
import games.uno.repositories.PlayerRepository;
import games.uno.web.domain.PresentableCard;
import games.uno.web.domain.PresentablePlayerHand;
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

    public void sendHandToAllPlayers(UnoCard onTop, Pair<Player, UnoCard> lastDrawnCard) {
        playerRepository.findAll().forEach(player -> {
            UnoCard drawn = lastDrawnCard == null ? null : lastDrawnCard.getKey().equals(player) ? lastDrawnCard.getValue() : null;
            sendPlayerHand(player, drawn, onTop);
        });
    }

    public void sendPlayerHand(Player player, UnoCard lastDrawn, UnoCard onTop) {
        List<PresentableCard> cards = PresentableCard.fromCollection(player.cardsOnHand(), onTop);
        PresentablePlayerHand playerHand = new PresentablePlayerHand(cards, PresentableCard.fromCard(lastDrawn, onTop));
        messagingTemplate.convertAndSendToUser(player.getName(), "/topic/game.cards", playerHand);
    }

    public void sendPlayersListToAll(List<Player> players) {
        messagingTemplate.convertAndSend("/topic/game.players", players);
    }
}
