package games.uno.web;

import games.uno.GameService;
import games.uno.PlayerService;
import games.uno.domain.game.Player;
import games.uno.util.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RequestMapping("/api/players")
@RestController
public class PlayerController {
    private final PlayerService playerService;
    private final GameService gameService;
    private final RandomDataGenerator generator;

    @Autowired
    public PlayerController(PlayerService playerService, GameService gameService, RandomDataGenerator generator) {
        this.playerService = playerService;
        this.gameService = gameService;
        this.generator = generator;
    }

    @SubscribeMapping("/game.players")
    public Collection<Player> retrievePlayers() {
        return gameService.players();
    }

    @SubscribeMapping("/game.cards")
    public PresentablePlayerHand playerCards(StompHeaderAccessor accessor) {
        Player player = playerService.find(accessor);
        List<PresentableCard> cards = player.cardsOnHand().stream()
                .map(card -> PresentableCard.fromCard(card, gameService.currentCard()))
                .collect(Collectors.toList());

        return new PresentablePlayerHand(cards, PresentableCard.fromCard(gameService.getLastDrawnCardBy(player), gameService.currentCard()));
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public Player create(@RequestParam(value = "username", defaultValue = "", required = false) String username,
                         @RequestParam(value = "password", required = false) String password,
                         HttpServletRequest request, Principal principal) {
        if (principal != null) {
            Player player = playerService.find(request.getSession().getId());

            if (player != null)
                return player;
        }

        return authorizeAndStore(generateUsernameIfEmpty(username), password, request.getSession().getId());
    }

    private Player authorizeAndStore(String username, String password, String sessionId) {
        Player player = new Player(username);
        playerService.authorizePlayer(player.getName(), null);

        return playerService.save(sessionId, player);
    }

    public String generateUsernameIfEmpty(String username) {
        return (username.isEmpty()) ? generator.name() : username;
    }
}