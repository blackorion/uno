package games.uno.websockets;

import games.uno.PlayerRepository;
import games.uno.domain.Card;
import games.uno.domain.Player;
import games.uno.web.PresentableCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayerEventInformer
{
    private SimpMessagingTemplate messagingTemplate;
    private PlayerRepository playerRepository;

    @Autowired
    public PlayerEventInformer(SimpMessagingTemplate messagingTemplate, PlayerRepository playerRepository) {
        this.messagingTemplate = messagingTemplate;
        this.playerRepository = playerRepository;
    }

    public void sendHandToAllPlayers(Card onTop) {
        playerRepository.findAll().forEach(player -> sendPlayerHand(player, onTop));
    }

    private void sendPlayerHand(Player player, Card onTop) {
        List<PresentableCard> cards = player.cardsOnHand().stream().map(card -> mapCard(card, onTop)).collect(Collectors.toList());
        messagingTemplate.convertAndSendToUser(player.getUsername(), "/topic/game.cards", cards);
    }

    private PresentableCard mapCard(Card card, Card onTop) {
        return new PresentableCard(card.getValue(), card.getColor(), card.isPlayable(onTop));
    }
}
